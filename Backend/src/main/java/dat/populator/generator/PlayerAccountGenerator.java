package dat.populator.generator;

import dat.entities.PlayerAccount;
import dat.entities.User;
import dat.enums.Game;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerAccountGenerator implements TestDataGenerator<PlayerAccount> {

    private final Set<String> usedNames = new HashSet<>();
    private final List<User> users;
    private final Game game;
    private final Random random;

    public PlayerAccountGenerator(List<User> users, Game game, Random random) {
        this.users = users;
        this.game = game;
        this.random = random;
    }

    @Override
    public List<PlayerAccount> generate() {
        return users.stream()
                .map(user -> createRandomPlayerAccount(game, user))
                .collect(Collectors.toList());
    }

    private PlayerAccount createRandomPlayerAccount(Game game, User user) {
        String name = generateUniquePlayerAccountName();
        boolean isActive = random.nextDouble() < 0.75; // 75%
        String rank = createRandomPlayerAccountRank(game);

        return new PlayerAccount(name, isActive, game, rank, user);
    }

    private String createRandomPlayerAccountRank(Game game) {
        if (game == Game.COUNTER_STRIKE) {
            return TestDataConstants.COUNTER_STRIKE_RANKS[random.nextInt(TestDataConstants.COUNTER_STRIKE_RANKS.length)];
        } else if (game == Game.ROCKET_LEAGUE) {
            int tier = random.nextInt(TestDataConstants.ROCKET_LEAGUE_TIERS.length);

            if (tier == TestDataConstants.ROCKET_LEAGUE_TIERS.length - 1) {
                return TestDataConstants.ROCKET_LEAGUE_TIERS[tier]; // SSL has no divisions
            }

            int division = random.nextInt(TestDataConstants.ROCKET_LEAGUE_TIER_DIVISIONS[tier]) + 1;

            return TestDataConstants.ROCKET_LEAGUE_TIERS[tier] + " " + toRoman(division);
        }

        return null;
    }

    private String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            default -> "";
        };
    }

    private String generateUniquePlayerAccountName() {
        String name;
        int attempts = 0;

        do {
            name = generatePlayerAccountName();
            attempts++;

            // If we somehow can't get a unique name after 1000 tries, append a UUID
            if (attempts > 1000) {
                name += "_" + UUID.randomUUID().toString().substring(0, 6);
                break;
            }
        } while (usedNames.contains(name));

        usedNames.add(name);
        return name;
    }

    private String generatePlayerAccountName() {
        double roll = random.nextDouble();

        if (roll < 0.45) { // 45%
            // adjectiveNoun
            return TestDataConstants.PA_ADJECTIVES[random.nextInt(TestDataConstants.PA_ADJECTIVES.length)]
                    + TestDataConstants.PA_NOUNS[random.nextInt(TestDataConstants.PA_NOUNS.length)]
                    + random.nextInt(1000); // add numeric suffix for uniqueness
        } else if (roll < 0.9) { // next 45%
            // gamerTag
            return TestDataConstants.PA_TAGS[random.nextInt(TestDataConstants.PA_TAGS.length)]
                    + TestDataConstants.PA_ADJECTIVES[random.nextInt(TestDataConstants.PA_ADJECTIVES.length)]
                    + random.nextInt(10000);
        } else { // last 10%
            // randomAlphanumeric
            int length = 7 + random.nextInt(5); // 7-11 chars
            StringBuilder sb = new StringBuilder();
            boolean hasLetter = false;
            boolean hasDigit = false;

            for (int i = 0; i < length; i++) {
                int charType = random.nextInt(3);

                if (charType == 0) {
                    sb.append((char) ('a' + random.nextInt(26)));
                    hasLetter = true;
                } else if (charType == 1) {
                    sb.append((char) ('A' + random.nextInt(26)));
                    hasLetter = true;
                } else {
                    sb.append(random.nextInt(10));
                    hasDigit = true;
                }
            }

            // Ensure at least one letter and one digit
            if (!hasLetter) sb.setCharAt(0, (char) ('A' + random.nextInt(26)));
            if (!hasDigit) sb.setCharAt(sb.length() - 1, (char) ('0' + random.nextInt(10)));

            return sb.toString();
        }
    }
}
