package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.UserDAO;
import dat.dtos.TeamDTO;
import dat.dtos.UserDTO;
import dat.entities.User;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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

    public void updateProfilePicture(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            String profilePicture = ctx.formParam("profilePicture");
            UserDTO userDTO = userDAO.getById(userId);
            if (userDTO == null) {
                throw new ApiException(404, "User not found");
            }
            userDAO.updateProfilePicture(userId, profilePicture);
            ctx.status(200);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }

    public void getProfilePicture(Context ctx) throws ApiException {
        try {
            int userId = Integer.parseInt(ctx.pathParam("id"));
            UserDTO userDTO = userDAO.getById(userId);
            if (userDTO == null) {
                throw new ApiException(404, "User not found");
            }
            ctx.json(userDTO.getProfilePicture());
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }


}
