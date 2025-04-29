package dat.populator.generator;

import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.User;
import dat.enums.Game;

import java.util.*;

public class TeamGenerator implements TestDataGenerator<Team> {

    private final Set<String> usedTeamNames = new HashSet<>();
    private final List<List<PlayerAccount>> teamAccounts;
    private final Game game;
    private final Random random;

    public TeamGenerator(List<List<PlayerAccount>> teamAccounts, Game game, Random random) {
        this.teamAccounts = teamAccounts;
        this.game = game;
        this.random = random;
    }

    @Override
    public List<Team> generate() {
        List<Team> teams = new ArrayList<>();

        for (List<PlayerAccount> accounts : teamAccounts) {
            String teamName = generateUniqueTeamName();
            PlayerAccount captainPlayerAccount = accounts.get(0);
            User teamCaptain = captainPlayerAccount.getUser();

            Team team = new Team(teamName, game, teamCaptain);
            team.setTeamAccounts(accounts);
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
