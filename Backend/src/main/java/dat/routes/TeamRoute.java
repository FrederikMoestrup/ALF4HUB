package dat.routes;


import dat.controllers.TeamController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TeamRoute {

    private final TeamController teamController = new TeamController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", teamController::getAll, Role.USER);
            get("/{id}",teamController::getById, Role.USER);
            get("/{id}/players", teamController::getPlayersByTeamId, Role.ANYONE);
            post("/", teamController::create, Role.ADMIN);
            put("/{id}", teamController::update, Role.ADMIN);
            delete("/{id}", teamController::delete, Role.ADMIN);
        };
    }
}
