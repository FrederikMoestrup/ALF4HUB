package dat.populator.generator;

import dat.entities.Tournament;
import dat.entities.User;
import dat.enums.Game;
import dat.enums.TournamentStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TournamentGenerator implements TestDataGenerator<Tournament> {

    private final Set<String> usedTournamentNames = new HashSet<>();
    private final List<User> hosts;
    private final Game game;
    private final int tournamentCount;
    private final int minTeamSize;
    private final int maxTeamSize;
    private final Random random;

    public TournamentGenerator(List<User> hosts, Game game, int tournamentCount, int minTeamSize, int maxTeamSize, Random random) {
        this.hosts = hosts;
        this.game = game;
        this.tournamentCount = tournamentCount;
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
        this.random = random;
    }

    @Override
    public List<Tournament> generate() {
        List<Tournament> tournaments = new ArrayList<>();

        for (int i = 0; i < tournamentCount; i++) {
            String name = generateUniqueTournamentName();
            int tournamentSize = minTeamSize + random.nextInt(maxTeamSize - minTeamSize + 1);
            double prizePool = 100 + (random.nextDouble() * 9900);
            String rules = TestDataConstants.RULE_SETS[random.nextInt(TestDataConstants.RULE_SETS.length)];
            String entryRequirements = TestDataConstants.ENTRY_REQUIREMENTS[random.nextInt(TestDataConstants.ENTRY_REQUIREMENTS.length)];

            TournamentStatus[] statuses = TournamentStatus.values();
            TournamentStatus status = statuses[random.nextInt(statuses.length)];

            User host = hosts.get(random.nextInt(hosts.size()));

            // Generate time info
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start, end;

            switch (status) {
                case NOT_STARTED:
                    start = now.plusDays(1 + random.nextInt(7)).withHour(18).withMinute(0).withSecond(0).withNano(0);
                    end = start.plusHours(2 + random.nextInt(5 * 24));
                    break;
                case IN_PROGRESS:
                    start = now.minusDays(1 + random.nextInt(3)).withHour(18).withMinute(0).withSecond(0).withNano(0);
                    end = now.plusDays(1 + random.nextInt(3)).withHour(22).withMinute(0).withSecond(0).withNano(0);
                    break;
                case COMPLETED:
                    start = now.minusDays(2 + random.nextInt(9)).withHour(15 + random.nextInt(8)).withMinute(0).withSecond(0).withNano(0);
                    end = start.plusHours(2 + random.nextInt(5 * 24));

                    if (end.isAfter(now.minusDays(1))) {
                        end = now.minusDays(1).withHour(22).withMinute(0).withSecond(0).withNano(0);
                    }
                    break;
                default:
                    start = now.plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
                    end = start.plusHours(2 + random.nextInt(5 * 24));
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String startDate = start.format(dateFormatter);
            String startTime = start.format(timeFormatter);
            String endDate = end.format(dateFormatter);
            String endTime = end.format(timeFormatter);

            Tournament tournament = new Tournament(
                    name,
                    game,
                    tournamentSize,
                    5,
                    prizePool,
                    rules,
                    entryRequirements,
                    status,
                    startDate,
                    startTime,
                    endDate,
                    endTime,
                    host
            );

            tournaments.add(tournament);
        }

        return tournaments;
    }

    private String generateUniqueTournamentName() {
        String name;
        int attempts = 0;

        do {
            name = game.getDisplayName() + " Tournament " + random.nextInt(100000);
            attempts++;

            if (attempts > 1000) {
                name += "_" + UUID.randomUUID().toString().substring(0, 6);
                break;
            }
        } while (usedTournamentNames.contains(name));

        usedTournamentNames.add(name);
        return name;
    }
}
