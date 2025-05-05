package dat.routes;


import dat.controllers.TournamentTeamController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TournamentTeamRoute {

    private final TournamentTeamController tournamentTeamController = new TournamentTeamController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", tournamentTeamController::getAll, Role.USER);
            get("/{id}",tournamentTeamController::getById, Role.USER);
            post("/", tournamentTeamController::create, Role.USER);
            put("/{id}", tournamentTeamController::update, Role.USER);
            delete("/{id}", tournamentTeamController::delete, Role.ADMIN);
        };
    }
}
