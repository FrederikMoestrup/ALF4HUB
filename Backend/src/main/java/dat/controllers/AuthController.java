package dat.controllers;

import dat.daos.UserDAO;
import dat.entities.User;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

public class AuthController {

    private UserDAO userDAO;

    public void login(Context ctx) throws ApiException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        if (username == null || password == null) {
            throw new ApiException(400, "Username and password are required");
        }

        // Find brugeren
        User user = userDAO.findByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new ApiException(401, "Invalid username or password");
        }

        ctx.sessionAttribute("user", user.getUsername());

        ctx.status(200).result("Login successful");
    }

    public void logout(Context ctx) {  }
    public void refreshToken(Context ctx) { }
    public void register(Context ctx) throws ApiException {  }
}
