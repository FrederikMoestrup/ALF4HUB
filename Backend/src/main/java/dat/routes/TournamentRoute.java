package dat.routes;

import dat.controllers.TournamentController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TournamentRoute {

    private final TournamentController tournamentController = new TournamentController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", tournamentController::getAll, Role.ANYONE);
            get("/{id}", tournamentController::getById, Role.ANYONE);
            post("/", tournamentController::create, Role.USER);
            put("/{id}", tournamentController::update, Role.USER);
            delete("/{id}", tournamentController::delete, Role.ADMIN);
            get("/{userId}/tournaments", tournamentController::getByUserId, Role.USER);
        };
    }
}
