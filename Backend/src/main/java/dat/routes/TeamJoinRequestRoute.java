package dat.routes;


import dat.controllers.TeamJoinRequestController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TeamJoinRequestRoute {

    private final TeamJoinRequestController teamJoinRequestController = new TeamJoinRequestController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", teamJoinRequestController::getAll, Role.USER);
            get("/{id}",teamJoinRequestController::getById, Role.USER);
            put("/requester/{user_id}/team/{team_id}/player_account/{player_account_id}", teamJoinRequestController::create, Role.USER);
            patch("/responder/{user_id}/join_request/{request_id}/status/{status}", teamJoinRequestController::respondToRequest, Role.USER);
            get("/team/{team_id}", teamJoinRequestController::getRequestsForTeam, Role.USER);
            get("/team_captain/{user_id}", teamJoinRequestController::getRequestsForTeamCaptain, Role.USER);
            delete("/{id}", teamJoinRequestController::delete, Role.ADMIN);
        };
    }
}
