package dat.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JOSEException;
import dat.utils.Utils;
import dat.config.HibernateConfig;
import dat.security.daos.SecurityDAO;
import dat.entities.User;
import dat.security.exceptions.ApiException;
import dat.security.exceptions.NotAuthorizedException;
import dat.security.exceptions.ValidationException;
import dat.security.tokenSecurity.ITokenSecurity;
import dat.security.tokenSecurity.TokenSecurity;
import dat.dtos.UserDTO;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityController implements ISecurityController {
    ObjectMapper objectMapper = new ObjectMapper();
    ITokenSecurity tokenSecurity = new TokenSecurity();
    private static SecurityDAO securityDAO;
    private static SecurityController instance;
    private static Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private SecurityController() { }

    public static SecurityController getInstance() { // Singleton because we don't want multiple instances of the same class
        if (instance == null) {
            instance = new SecurityController();
            securityDAO = new SecurityDAO(HibernateConfig.getEntityManagerFactory("ALF4HUB_DB"));
        }
        return instance;
    }
    public static SecurityController getInstanceForTest(EntityManagerFactory _emf) { // Singleton because we don't want multiple instances of the same class
        if (instance == null) {
            instance = new SecurityController();
        }
        securityDAO = new SecurityDAO(_emf);
        return instance;
    }

    @Override
    public Handler login() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode(); // for sending json messages back to the client
            try {
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                UserDTO verifiedUser = securityDAO.getVerifiedUser(user.getUsername(), user.getPassword());
                String token = createToken(verifiedUser);

                ctx.status(200).json(returnObject
                        .put("token", token)
                        .put("username", verifiedUser.getUsername()));

            } catch (EntityNotFoundException | ValidationException e) {
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };
    }

    @Override
    public Handler register() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User created = securityDAO.createUser(userInput.getUsername(), userInput.getPassword(), userInput.getEmail());

                String token = createToken(new UserDTO(created.getUsername(), Set.of("USER")));
                ctx.status(HttpStatus.CREATED).json(returnObject
                        .put("token", token)
                        .put("username", created.getUsername()));
            } catch (EntityExistsException e) {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(returnObject.put("msg", "User already exists"));
            }
        };
    }

    @Override
    public Handler authenticate() throws UnauthorizedResponse {

        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {
            // This is a preflight request => OK
            if (ctx.method().toString().equals("OPTIONS")) {
                ctx.status(200);
                return;
            }
            String header = ctx.header("Authorization");
            if (header == null) {
                throw new UnauthorizedResponse("Authorization header missing");
            }

            String[] headerParts = header.split(" ");
            if (headerParts.length != 2) {
                throw new UnauthorizedResponse("Authorization header malformed");
            }

            String token = headerParts[1];
            UserDTO verifiedTokenUser = verifyToken(token);

            if (verifiedTokenUser == null) {
                throw new UnauthorizedResponse("Invalid User or Token");
            }
            logger.info("User verified: " + verifiedTokenUser);
            ctx.attribute("user", verifiedTokenUser);
        };
    }

    @Override
    // Check if the user's roles contain any of the allowed roles
    public boolean authorize(UserDTO user, Set<RouteRole> allowedRoles) {
        if (user == null) {
            throw new UnauthorizedResponse("You need to log in, dude!");
        }
        Set<String> roleNames = allowedRoles.stream()
                .map(RouteRole::toString)  // Convert RouteRoles to  Set of Strings
                .collect(Collectors.toSet());
        return user.getRoles().stream()
                .map(String::toUpperCase)
                .anyMatch(roleNames::contains);
    }

    @Override
    public String createToken(UserDTO user) {
        try {
            String ISSUER;
            String TOKEN_EXPIRE_TIME;
            String SECRET_KEY;

            if (System.getenv("DEPLOYED") != null) {
                ISSUER = System.getenv("ISSUER");
                TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
                SECRET_KEY = System.getenv("SECRET_KEY");
            } else {
                ISSUER = Utils.getPropertyValue("ISSUER", "config.properties");
                TOKEN_EXPIRE_TIME = Utils.getPropertyValue("TOKEN_EXPIRE_TIME", "config.properties");
                SECRET_KEY = Utils.getPropertyValue("SECRET_KEY", "config.properties");
            }
            return tokenSecurity.createToken(user, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }

    @Override
    public UserDTO verifyToken(String token) {
        boolean IS_DEPLOYED = (System.getenv("DEPLOYED") != null);
        String SECRET = IS_DEPLOYED ? System.getenv("SECRET_KEY") : Utils.getPropertyValue("SECRET_KEY", "config.properties");

        try {
            if (tokenSecurity.tokenIsValid(token, SECRET) && tokenSecurity.tokenNotExpired(token)) {
                return tokenSecurity.getUserWithRolesFromToken(token);
            } else {
                throw new NotAuthorizedException(403, "Token is not valid");
            }
        } catch (ParseException | JOSEException | NotAuthorizedException e) {
            e.printStackTrace();
            throw new ApiException(HttpStatus.UNAUTHORIZED.getCode(), "Unauthorized. Could not verify token");
        }
    }

    public @NotNull Handler addRole() {
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode();
            try {
                // get the role from the body. the json is {"role": "manager"}.
                // We need to get the role from the body and the username from the token
                String newRole = ctx.bodyAsClass(ObjectNode.class).get("role").asText();
                int id = Integer.parseInt(ctx.pathParam("id"));
                User updatedUser = securityDAO.addRole(id, newRole);
                ctx.status(200).json(returnObject.put("msg", "Role " + newRole + " added to user"));
            } catch (EntityNotFoundException e) {
                ctx.status(404).json("{\"msg\": \"User not found\"}");
            }
        };
    }

    // Health check for the API. Used in deployment
    public void healthCheck(@NotNull Context ctx) {
        ctx.status(200).json("{\"msg\": \"API is up and running\"}");
    }

    public static SecurityDAO getSecurityDAO() {
        return securityDAO;
    }
}