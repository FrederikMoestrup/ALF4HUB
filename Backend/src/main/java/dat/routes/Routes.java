package dat.routes;


import dat.controllers.TeamJoinRequestController;
import dat.controllers.TournamentTeamController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final TeamRoute teamRoute = new TeamRoute();
    private final PlayerAccountRoute playerAccountRoute = new PlayerAccountRoute();
    private final TournamentRoute tournamentRoute = new TournamentRoute();
    private final TournamentTeamRoute tournamentTeamRoute = new TournamentTeamRoute();
    private final TeamJoinRequestRoute teamJoinRequestRoute = new TeamJoinRequestRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/users", new UserRoute().getRoutes());
            path("/teams", teamRoute.getRoutes());
            path("/player-accounts", playerAccountRoute.getRoutes());
            path("/tournaments", tournamentRoute.getRoutes());
            path("/tournament-teams", tournamentTeamRoute.getRoutes());
            path("/team-join-requests", teamJoinRequestRoute.getRoutes());
        };
    }
}
