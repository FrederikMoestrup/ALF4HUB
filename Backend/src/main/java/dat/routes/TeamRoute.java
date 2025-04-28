package dat.routes;


import dat.controllers.TeamController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import org.eclipse.jetty.security.RoleRunAsToken;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TeamRoute {

    private final TeamController teamController = new TeamController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", teamController::getAll, Role.USER);
            get("/{id}",teamController::getById, Role.USER);
            post("/", teamController::create, Role.ADMIN);
            put("/{id}", teamController::update, Role.ADMIN);
            delete("/{id}", teamController::delete, Role.ADMIN);
            post("/{id}/invite-player", teamController::invitePlayer, Role.ANYONE);//maybe change role to TeamCaptain at some point?
            delete("/{id}/remove-player", teamController::removePlayer, Role.TEAM_CAPTAIN);
        };
    }
}
