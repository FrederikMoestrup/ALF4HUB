package dat.routes;

import dat.controllers.UserController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoute {

    private final UserController userController = new UserController();

    protected EndpointGroup getRoutes() {

        return () -> {
            put("/{id}/strike", userController::addStrike, Role.USER);
            get("/{id}/profile_picture", userController::getProfilePicture, Role.USER);
            put("/{id}/profile_picture", userController::updateProfilePicture, Role.USER);
        };
    }
}
