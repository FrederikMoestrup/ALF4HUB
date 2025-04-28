package dat.populator.generator;

import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.enums.Game;

import java.util.*;

public class TeamGenerator implements TestDataGenerator<Team> {

    private final Set<String> usedTeamNames = new HashSet<>();
    private final List<PlayerAccount> playerAccounts;
    private final Game game;
    private final int teamSize;
    private final int teamCount;
    private final Random random;

    public TeamGenerator(List<PlayerAccount> playerAccounts, Game game, int teamSize, int teamCount, Random random) {
        this.playerAccounts = playerAccounts;
        this.game = game;
        this.teamSize = teamSize;
        this.teamCount = teamCount;
        this.random = random;
    }

    @Override
    public List<Team> generate() {
        List<Team> teams = new ArrayList<>();

        int totalAccountsNeeded = teamCount * teamSize;
        if (playerAccounts.size() < totalAccountsNeeded) {
            throw new IllegalArgumentException("Not enough player accounts for requested teams and team size.");
        }

        for (int i = 0; i < teamCount; i++) {
            List<PlayerAccount> teamAccounts = playerAccounts.subList(i * teamSize, (i + 1) * teamSize);
            String teamName = generateUniqueTeamName();
            PlayerAccount captain = teamAccounts.get(random.nextInt(teamAccounts.size()));

            Team team = new Team(teamName, game, captain.getUser());
            team.setTeamAccounts(new ArrayList<>(teamAccounts));
            teams.add(team);
        }

        return teams;
    }

    private String generateUniqueTeamName() {
        String name;
        int attempts = 0;

        do {
            name = generateTeamName();
            attempts++;

            if (attempts > 1000) {
                name += "_" + UUID.randomUUID().toString().substring(0, 6);
                break;
            }
        } while (usedTeamNames.contains(name));

        usedTeamNames.add(name);
        return name;
    }

    private String generateTeamName() {
        int type = random.nextInt(4);

        return switch (type) {
            case 0 -> // prefixSuffixCombo
                    TestDataConstants.TEAM_PREFIXES[random.nextInt(TestDataConstants.TEAM_PREFIXES.length)] +
                            " " + TestDataConstants.TEAM_SUFFIXES[random.nextInt(TestDataConstants.TEAM_SUFFIXES.length)];
            case 1 -> // animalTheme
                    "The " + TestDataConstants.TEAM_ANIMALS[random.nextInt(TestDataConstants.TEAM_ANIMALS.length)];
            case 2 -> // gamerTagStyle
                    TestDataConstants.TEAM_GAMERTAGS[random.nextInt(TestDataConstants.TEAM_GAMERTAGS.length)] +
                            TestDataConstants.TEAM_GAMERTAGS[random.nextInt(TestDataConstants.TEAM_GAMERTAGS.length)] +
                            random.nextInt(100);
            default -> // abstractConcept
                    TestDataConstants.MODIFIERS[random.nextInt(TestDataConstants.MODIFIERS.length)] +
                            " " + TestDataConstants.ABSTRACT_CONCEPTS[random.nextInt(TestDataConstants.ABSTRACT_CONCEPTS.length)];
        };
    }
}
