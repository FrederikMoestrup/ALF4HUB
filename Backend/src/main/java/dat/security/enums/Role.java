package dat.security.enums;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    ANYONE, USER, ADMIN, PLAYER, MODERATOR, TEAM_CAPTAIN;
}
