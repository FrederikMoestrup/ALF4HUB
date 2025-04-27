package dat.populator;

import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.Tournament;
import dat.entities.User;
import dat.enums.Game;
import dat.populator.generator.PlayerAccountGenerator;
import dat.populator.generator.TeamGenerator;
import dat.populator.generator.TournamentGenerator;
import dat.populator.generator.UserGenerator;
import dat.security.entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Populator {

    private static final long DEFAULT_SEED = 0L;
    private static Populator instance;

    private final EntityManagerFactory emf;
    private final Random random;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
        this.random = new Random(DEFAULT_SEED);
    }

    public static synchronized Populator getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new Populator(emf);
        }

        return instance;
    }

    public void populateTestData() {
        // Step 1: Roles
        List<Role> roles = createRoles();
        //persist(roles);

        // Step 2: Users
        List<User> users = createUsers(roles);
        persist(users);

        // Step 3: Player Accounts
        List<PlayerAccount> playerAccounts = createPlayerAccounts(users);
        //persist(playerAccounts);

        // Step 4: Teams
        List<Team> teams = createTeams(playerAccounts);
        //persist(teams);

        // Step 5: Tournaments
        List<Tournament> tournaments = createTournaments(users, teams);
        persist(tournaments);
    }

    public List<Role> createRoles() {
        return List.of(
                new Role("user"),
                new Role("admin"),
                new Role("player"),
                new Role("moderator"),
                new Role("captain")
        );
    }

    public List<User> createUsers(List<Role> roles) {
        List<UserGenerator.UserType> userTypes = List.of(
                new UserGenerator.UserType("User", List.of(roles.get(0), roles.get(2)), 16),
                new UserGenerator.UserType("Admin", List.of(roles.get(1)), 2)
        );

        UserGenerator userGenerator = new UserGenerator(userTypes, "1234");

        return userGenerator.generate();
    }

    public List<PlayerAccount> createPlayerAccounts(List<User> users) {
        List<User> counterStrikeUsers = users.subList(0, 10);
        List<User> rocketLeagueUsers = users.subList(4, 16);

        PlayerAccountGenerator counterStrikePlayerAccountGenerator = new PlayerAccountGenerator(counterStrikeUsers, Game.COUNTER_STRIKE, random);
        PlayerAccountGenerator rocketLeaguePlayerAccountGenerator = new PlayerAccountGenerator(rocketLeagueUsers, Game.ROCKET_LEAGUE, random);

        List<PlayerAccount> playerAccounts = new ArrayList<>();
        playerAccounts.addAll(counterStrikePlayerAccountGenerator.generate());
        playerAccounts.addAll(rocketLeaguePlayerAccountGenerator.generate());

        return playerAccounts;
    }

    public List<Team> createTeams(List<PlayerAccount> playerAccounts) {
        List<PlayerAccount> counterStrikePlayerAccounts = playerAccounts.subList(0, 10);
        List<PlayerAccount> rocketLeaguePlayerAccounts = playerAccounts.subList(10, 19);

        TeamGenerator counterStrikeTeamGenerator = new TeamGenerator(counterStrikePlayerAccounts, Game.COUNTER_STRIKE, 5, 2, random);
        TeamGenerator rocketLeagueTeamGenerator = new TeamGenerator(rocketLeaguePlayerAccounts, Game.ROCKET_LEAGUE, 3, 3, random);

        List<Team> teams = new ArrayList<>();
        teams.addAll(counterStrikeTeamGenerator.generate());
        teams.addAll(rocketLeagueTeamGenerator.generate());

        return teams;
    }

    public List<Tournament> createTournaments(List<User> users, List<Team> teams) {
        List<User> hosts = users.subList(16, 18);

        TournamentGenerator counterStrikeTournamentGenerator = new TournamentGenerator(
                teams.stream().filter(t -> t.getGame() == Game.COUNTER_STRIKE).toList(),
                hosts, Game.COUNTER_STRIKE, 1, 2, 5, random
        );
        TournamentGenerator rocketLeagueTournamentGenerator = new TournamentGenerator(
                teams.stream().filter(t -> t.getGame() == Game.ROCKET_LEAGUE).toList(),
                hosts, Game.ROCKET_LEAGUE, 1, 2, 3, random
        );

        List<Tournament> tournaments = new ArrayList<>();
        tournaments.addAll(counterStrikeTournamentGenerator.generate());
        tournaments.addAll(rocketLeagueTournamentGenerator.generate());

        return tournaments;
    }

    public void persist(List<?> entities) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            entities.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    public void cleanup(Class<?> entityClazz) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM " + entityClazz.getSimpleName()).executeUpdate();
            em.getTransaction().commit();
        }
    }
}
