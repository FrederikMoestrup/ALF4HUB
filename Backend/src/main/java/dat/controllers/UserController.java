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
}
