package dat.populator.generator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataConstants {

    // Counter-Strike 2 Ranks
    public static final String[] COUNTER_STRIKE_RANKS = {
            "Silver 1", "Silver 2", "Silver 3", "Silver 4", "Silver Elite", "Silver Elite Master",
            "Gold Nova 1", "Gold Nova 2", "Gold Nova 3", "Gold Nova Master",
            "Master Guardian 1", "Master Guardian 2", "Master Guardian Elite",
            "Distinguished Master Guardian", "Legendary Eagle", "Legendary Eagle Master",
            "Supreme Master First Class", "Global Elite"
    };

    // Rocket League Tiers and Divisions
    public static final String[] ROCKET_LEAGUE_TIERS = {
            "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Champion", "Grand Champion", "Supersonic Legend"
    };
    public static final int[] ROCKET_LEAGUE_TIER_DIVISIONS = {3, 3, 3, 3, 3, 3, 3, 1};

    // Player Account Name Data
    public static final String[] PA_ADJECTIVES = {
            "Stealth", "Phantom", "Iron", "Swift", "Shadow", "Silent", "Blazing", "Frozen", "Mystic", "Thunder"
    };
    public static final String[] PA_NOUNS = {
            "Assassin", "Hunter", "Warden", "Ninja", "Ranger", "Sentinel", "Viper", "Falcon", "Wolf", "Dragon"
    };
    public static final String[] PA_TAGS = {
            "xX", "Pro", "OG", "Elite", "L33T", "Nova", "Alpha", "Sigma"
    };

    // Team Name Data
    public static final String[] TEAM_PREFIXES = {
            "Shadow", "Neon", "Quantum", "Phoenix", "Void",
            "Omega", "Celestial", "Inferno", "Nova", "Apex"
    };
    public static final String[] TEAM_SUFFIXES = {
            "Strikers", "Dominion", "Vanguard", "Legacy", "Dynasty",
            "Vortex", "Horizon", "Eclipse", "Renegades", "Protocol"
    };
    public static final String[] TEAM_ANIMALS = {
            "Wolves", "Dragons", "Hawks", "Vipers", "Lions",
            "Cobras", "Raptors", "Sharks", "Titans", "Rhinos"
    };
    public static final String[] TEAM_GAMERTAGS = {
            "xX", "Pro", "Elite", "L33T", "V3",
            "Xx", "OG", "Nova", "Alpha", "Sigma"
    };

    // Tournament Data
    public static final String[] RULE_SETS = {
            "Standard rules apply.",
            "No cheating allowed.",
            "Double elimination.",
            "Single elimination.",
            "Best of 3 matches."
    };
    public static final String[] ENTRY_REQUIREMENTS = {
            "Must be over 18.",
            "Invite only.",
            "Open to all."
    };
    public static final String[] STATUSES = {
            "UPCOMING",
            "ONGOING",
            "COMPLETED"
    };

    // Abstract Concepts for Team Names
    public static final String[] ABSTRACT_CONCEPTS = {
            "Chaos", "Infinity", "Eternity", "Paradox", "Nebula"
    };
    public static final String[] MODIFIERS = {
            "Rising", "Falling", "Eternal", "Silent", "Void"
    };
}
