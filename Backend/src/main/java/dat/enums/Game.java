package dat.enums;

import lombok.Getter;

@Getter
public enum Game {
    COUNTER_STRIKE("Counter-Strike"),
    LEAGUE_OF_LEGENDS("League of Legends"),
    DOTA_2("Dota 2"),
    VALORANT("Valorant"),
    ROCKET_LEAGUE("Rocket League");

    private final String displayName;

    Game(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
