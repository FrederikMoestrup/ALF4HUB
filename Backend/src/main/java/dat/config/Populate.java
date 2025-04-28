package dat.config;

import dat.dtos.PlayerAccountDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.Tournament;
import dat.entities.User;
import dat.enums.Game;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dat.enums.Game.DOTA_2;
import static dat.enums.Game.LEAGUE_OF_LEGENDS;

public class Populate {

    public static void populateDatabase(EntityManagerFactory emf) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            User user = new User();
            user.setUsername("TestUser");
            user.setPassword("TestPassword");

            PlayerAccount playerAccount = new PlayerAccount();
            playerAccount.setPlayAccountName("TestAccount");
            playerAccount.setActive(true);
            playerAccount.setGame(Game.LEAGUE_OF_LEGENDS);
            playerAccount.setRank("Gold");

            // Link them
            playerAccount.setUser(user);
            user.setPlayerAccounts(List.of(playerAccount)); // <-- CAUSES cycle
            // Persist the user and player account
            em.persist(user);
            em.persist(playerAccount);

            em.getTransaction().commit();

            //PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO(playerAccount);



           /* Tournament tournament1 = new Tournament();
            tournament1.setTournamentName("League of Legends Championship");
            tournament1.setStartDate("2025-06-01");
            tournament1.setEndDate("2025-06-05");
            tournament1.setStartTime("10:00");
            tournament1.setEndTime("18:00");
            tournament1.setGame(LEAGUE_OF_LEGENDS);
            tournament1.setStatus("UPCOMING");
            tournament1.setRules("Standard rules apply");
            tournament1.setEntryRequirements("None");
            tournament1.setPrizePool(5000.0);
            tournament1.setTeamSize(5);
            tournament1.setTournamentSize(16);

            Tournament tournament2 = new Tournament();
            tournament2.setTournamentName("DOTA 2 International");
            tournament2.setStartDate("2025-07-01");
            tournament2.setEndDate("2025-07-10");
            tournament2.setStartTime("11:00");
            tournament2.setEndTime("20:00");
            tournament2.setGame(DOTA_2);
            tournament2.setStatus("UPCOMING");
            tournament2.setRules("DOTA 2 rules");
            tournament2.setEntryRequirements("Invite only");
            tournament2.setPrizePool(10000.0);
            tournament2.setTeamSize(5);
            tournament2.setTournamentSize(16);

            // Create teams and players
            List<Team> lolTeam = getLolTeam();
            List<Team> dotaTeam = getDotaTeam();
            List<PlayerAccount> playeracc1 = getPlayerAcc1();
            List<PlayerAccount> playeracc2 = getPlayerAcc2();

            // Persist
            for (Team team : lolTeam) {
                em.persist(team.getTeamCaptain());
            }
            for (Team team : dotaTeam) {
                em.persist(team.getTeamCaptain());
            }
            for (PlayerAccount player : playeracc1) {
                em.persist(player.getUser());
            }
            for (PlayerAccount player : playeracc2) {
                em.persist(player.getUser());
            }

            // Assign teams to tournaments
            tournament1.setTeams(lolTeam);
            tournament2.setTeams(dotaTeam);

            // Assign players to teams
            playeracc1.get(0).setTeam(List.of(lolTeam.get(0)));
            playeracc1.get(1).setTeam(List.of(lolTeam.get(1)));
            playeracc1.get(2).setTeam(List.of(lolTeam.get(2)));

            playeracc2.get(0).setTeam(List.of(dotaTeam.get(0)));
            playeracc2.get(1).setTeam(List.of(dotaTeam.get(1)));
            playeracc2.get(2).setTeam(List.of(dotaTeam.get(2)));

            // Persist teams
            for (Team team : lolTeam) {
                em.persist(team);
            }
            for (Team team : dotaTeam) {
                em.persist(team);
            }

            // Persist tournaments
            em.persist(tournament1);
            em.persist(tournament2);

            // Persist players
            for (PlayerAccount player : playeracc1) {
                em.persist(player);
            }
            for (PlayerAccount player : playeracc2) {
                em.persist(player);
            }

            em.getTransaction().commit();

            */
        }
    }

    @NotNull
        private static List<Team> getLolTeam(){
        Team t1 = new Team("Supra", LEAGUE_OF_LEGENDS, new User("Cap1","1234"));
        Team t2 = new Team("Champions", LEAGUE_OF_LEGENDS, new User("Cap2","1234"));
        Team t3 = new Team("Eagles", LEAGUE_OF_LEGENDS, new User("Cap3","1234"));

        Team[] arrayTeam = {t1, t2, t3};
        return List.of(arrayTeam);
    }

    @NotNull
        private static List<Team> getDotaTeam(){
        Team t1 = new Team("Brainstorm Titans", DOTA_2, new User("Cap4","1234"));
        Team t2 = new Team("Alliance", DOTA_2, new User("Cap5","1234"));
        Team t3 = new Team("The Caffeine crew", DOTA_2, new User("Cap6","1234"));

        Team[] arrayTeam = {t1, t2, t3};
        return List.of(arrayTeam);
    }

    @NotNull
        private static List<PlayerAccount> getPlayerAcc1() {
        PlayerAccount p1 = new PlayerAccount("Mads Mikkelsen", true, LEAGUE_OF_LEGENDS, "Bronze", new User("User1", "1234"));
        PlayerAccount p2 = new PlayerAccount("Nikolaj Coster", true, LEAGUE_OF_LEGENDS, "Silver", new User("User2", "1234"));
        PlayerAccount p3 = new PlayerAccount("Pilou Asbæk", true, LEAGUE_OF_LEGENDS, "Gold", new User("User3", "1234"));

        PlayerAccount[] arrayPlayAccount = {p1, p2, p3};
        return List.of(arrayPlayAccount);
    }

    @NotNull
        private static List<PlayerAccount> getPlayerAcc2() {
        PlayerAccount p1 = new PlayerAccount("Anders Matthesen", true, DOTA_2, "Platinum", new User("User4", "1234"));
        PlayerAccount p2 = new PlayerAccount("Mick Øgendahl", true, DOTA_2, "Diamond", new User("User5", "1234"));
        PlayerAccount p3 = new PlayerAccount("Jonatan Spang", true, DOTA_2, "Challenger", new User("User6", "1234"));

        PlayerAccount[] arrayPlayAccount = {p1, p2, p3};
        return List.of(arrayPlayAccount);
    }

    public static void clearDatabase(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // delete from all tables
            //em.createQuery("DELETE FROM TeamAccount").executeUpdate();
            em.createQuery("DELETE FROM Team").executeUpdate();
            em.createQuery("DELETE FROM Tournament").executeUpdate();
            em.createQuery("DELETE FROM PlayerAccount").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();

            // reset sequences
            em.createNativeQuery("ALTER SEQUENCE player_account_player_account_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE team_team_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE tournament_tournament_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE users_id_seq RESTART WITH 1").executeUpdate();

            em.getTransaction().commit();
        }
    }
}
