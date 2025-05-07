package dat.routes;

import dat.controllers.TournamentController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TournamentRoute {

    private final TournamentController tournamentController = new TournamentController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", tournamentController::getAll, Role.USER);
            get("/{id}", tournamentController::getById, Role.USER);
            post("/", tournamentController::create, Role.ADMIN);
            put("/{id}", tournamentController::update, Role.ADMIN);
            delete("/{id}", tournamentController::delete, Role.ADMIN);
        };
    }
}
