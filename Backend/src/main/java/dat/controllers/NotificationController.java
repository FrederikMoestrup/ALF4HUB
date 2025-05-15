package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.NotificationDAO;
import dat.daos.UserDAO;
import dat.dtos.NotificationDTO;
import dat.dtos.UserDTO;
import dat.entities.User;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

import java.util.Map;


public class NotificationController {

    private final NotificationDAO notificationDAO;
    private final UserDAO userDAO;

    public NotificationController() {
        EntityManagerFactory emf;

        if (HibernateConfig.getTest()) {
            emf = HibernateConfig.getEntityManagerFactoryForTest();
        } else {
            emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
        }

        this.notificationDAO = NotificationDAO.getInstance(emf);
        this.userDAO = UserDAO.getInstance(emf);
    }


    public void createNotification(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(401).json(Map.of("error", "Ikke logget ind"));
            return;
        }

        String username = userDTO.getUsername();

        try {
            NotificationDTO dto = ctx.bodyAsClass(NotificationDTO.class);
            User user = userDAO.getUserByUsername(username);
            NotificationDTO created = notificationDAO.create(dto, user);
            ctx.status(201).json(created);
        } catch (Exception e) {
            ctx.status(404).json(Map.of("error", e.getMessage()));
        }
    }



    public void getUnreadCount(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Ikke logget ind"));
            return;
        }

        String username = userDTO.getUsername();
        long count = notificationDAO.countUnreadForUser(username);
        ctx.json(Map.of("count", count));
    }


    public void getAllForUser(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Ikke logget ind"));
            return;
        }

        String username = userDTO.getUsername();
        ctx.json(notificationDAO.getAllForUser(username));
    }

    public void getNotificationCountForUser(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Ikke logget ind"));
            return;
        }
        String username = userDTO.getUsername();
        long count = notificationDAO.getNotificationCountForUser(username);
        ctx.json(Map.of("count", count));
    }


    public void markAsRead(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Ikke logget ind"));
            return;
        }

        String username = userDTO.getUsername();
        int id = Integer.parseInt(ctx.pathParam("id"));

        try {
            NotificationDTO updated = notificationDAO.markAsRead(id, username);
            ctx.json(updated);
        } catch (Exception e) {
            ctx.status(HttpStatus.NOT_FOUND).json(Map.of("error", e.getMessage()));
        }
    }


    public void markAllAsRead(Context ctx) {
        UserDTO userDTO = ctx.attribute("user");
        if (userDTO == null) {
            ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Ikke logget ind"));
            return;
        }

        String username = userDTO.getUsername();
        int updatedCount = notificationDAO.markAllAsRead(username);
        ctx.json(Map.of("updated", updatedCount));
    }
}

