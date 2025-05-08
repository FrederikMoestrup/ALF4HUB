package dat.routes;

import dat.controllers.PlayerAccountController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlayerAccountRoute {

    private final PlayerAccountController playerAccountController = new PlayerAccountController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", playerAccountController::getAll, Role.ANYONE);
            get("/{id}", playerAccountController::getById, Role.USER);
            post("/", playerAccountController::create, Role.USER);
            put("/{id}", playerAccountController::update, Role.USER);
            put("/{id}/status", playerAccountController::updateStatus, Role.ANYONE);
            delete("/{id}", playerAccountController::delete, Role.ADMIN);
        };
    }
}
