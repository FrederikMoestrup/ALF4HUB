package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.UserDAO;
import dat.dtos.ProfilePictureDTO;
import dat.dtos.TeamDTO;
import dat.dtos.UserDTO;
import dat.entities.User;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;

public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        if  (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.userDAO = UserDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.userDAO = UserDAO.getInstance(emf);
        }
    }

    public void addStrike(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            UserDTO userDTO = userDAO.getById(userId);
            if (userDTO == null) {
                throw new ApiException(404, "User not found");
            }
            userDTO = userDAO.addStrike(userId);
            ctx.json(userDTO, UserDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }

    public void getUserById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UserDTO userDTO = userDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(userDTO, UserDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id");
        }
    }

    public void updateUser(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

            // Verify user exists
            UserDTO existingUser = userDAO.getById(userId);
            if (existingUser == null) {
                throw new ApiException(404, "User not found");
            }

            // Update user
            UserDTO updatedUser = userDAO.update(userId, userDTO);
            ctx.json(updatedUser, UserDTO.class);
            ctx.status(200);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }


    public void updateProfilePicture(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));

            ProfilePictureDTO dto = null;
            try {
                dto = ctx.bodyAsClass(ProfilePictureDTO.class);
            } catch (Exception ignored) {}

            String profilePicture = (dto != null && dto.getProfilePicture() != null)
                    ? dto.getProfilePicture()
                    : ctx.formParam("profilePicture");

            if (profilePicture == null || profilePicture.isEmpty()) {
                throw new ApiException(400, "Missing profile picture URL");
            }
            
            userDAO.updateProfilePicture(userId, profilePicture);

            UserDTO updated = userDAO.getById(userId);
            ctx.json(updated, UserDTO.class);
            ctx.status(200);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                throw new ApiException(404, "User not found");
            }
            throw e;
        }
    }


    public void getProfilePicture(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            String profilePicture = userDAO.getProfilePictureById(userId);

            ctx.json(Map.of("profilePicture", profilePicture != null ? profilePicture : ""));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                throw new ApiException(404, "User not found");
            }
            throw e;
        }
    }


}
