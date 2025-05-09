package dat.routes;


import dat.controllers.TournamentTeamController;
import io.javalin.apibuilder.EndpointGroup;

import static dat.security.routes.SecurityRoutes.getSecuredRoutes;
import static dat.security.routes.SecurityRoutes.getSecurityRoutes;
import static io.javalin.apibuilder.ApiBuilder.*;


public class Routes {

    private final TeamRoute teamRoute = new TeamRoute();
    private final PlayerAccountRoute playerAccountRoute = new PlayerAccountRoute();
    private final TournamentRoute tournamentRoute = new TournamentRoute();
    private final TournamentTeamRoute tournamentTeamRoute = new TournamentTeamRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/users", new UserRoute().getRoutes());
            path("/teams", teamRoute.getRoutes());
            path("/player-accounts", playerAccountRoute.getRoutes());
            path("/tournaments", tournamentRoute.getRoutes());
            path("/", getSecurityRoutes());
            path("/", getSecuredRoutes());
            path("/tournament-teams", tournamentTeamRoute.getRoutes());
        };
    }
}
