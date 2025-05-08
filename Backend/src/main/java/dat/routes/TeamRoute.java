package dat.routes;


import dat.controllers.TeamController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TeamRoute {

    private final TeamController teamController = new TeamController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", teamController::getAll, Role.ANYONE);
            get("/{id}", teamController::getById, Role.ANYONE);
            post("/", teamController::create, Role.ANYONE);
            put("/{id}", teamController::update, Role.ANYONE);
            delete("/{id}", teamController::delete, Role.ANYONE);
            post("/{id}/invite-player", teamController::invitePlayer, Role.ANYONE);//maybe change role to TeamCaptain at some point?
            delete("/{id}/remove-player", teamController::removePlayer, Role.ANYONE); //yes should prob be TEAM_CAPTAIN
        };
    }
}
