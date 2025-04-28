package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.UserDAO;
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
            User user = userDAO.findById(userId);
            if (user == null) {
                throw new ApiException(404, "User not found");
            }
            user.addStrike();
            userDAO.update(user);
            ctx.status(200).json(new UserDTO(user));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }



}
