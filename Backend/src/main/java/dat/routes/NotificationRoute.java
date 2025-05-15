package dat.routes;

import dat.controllers.NotificationController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class NotificationRoute {

    private final NotificationController controller = new NotificationController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/unread-count", controller::getUnreadCount, Role.USER);
            get("/", controller::getAllForUser, Role.USER);
            get("/total-count", controller::getNotificationCountForUser, Role.USER);
            post("/", controller::createNotification, Role.USER);
            put("/{id}/read", controller::markAsRead, Role.USER);
            put("/markallasread", controller::markAllAsRead, Role.USER);
        };
    }

}

