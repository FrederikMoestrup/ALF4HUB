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
            get("/{id}/players", teamController::getPlayersByTeamId, Role.ANYONE);
            post("/team-captain/{id}", teamController::create, Role.USER);
            put("/{id}", teamController::update, Role.USER);
            delete("/{id}", teamController::delete, Role.USER);
            post("/{id}/invite-player/{playerAccountId}", teamController::invitePlayer, Role.USER);//maybe change role to TeamCaptain at some point?
            delete("/{id}/remove-player/{playerAccountId}", teamController::removePlayer, Role.USER); //yes should prob be TEAM_CAPTAIN


            post("/{teamId}/applications/{playerAccountId}/accept", teamController::acceptPlayerApplication, Role.USER);


            post("/{teamId}/applications/{playerAccountId}/reject", teamController::rejectPlayerApplication, Role.USER);

        };
    }
}
