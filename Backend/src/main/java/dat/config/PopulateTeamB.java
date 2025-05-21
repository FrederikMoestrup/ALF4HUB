package dat.config;


import dat.entities.*;
import dat.enums.TournamentStatus;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

import static dat.enums.Game.DOTA_2;
import static dat.enums.Game.LEAGUE_OF_LEGENDS;

public class PopulateTeamB {

    public static void populateDatabase(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Captains
            User cap1 = new User("Cap1", "1234");
            cap1.setEmail("cap1@example.com");
            User cap2 = new User("Cap2", "1234");
            cap2.setEmail("cap2@example.com");
            User cap3 = new User("Cap3", "1234");
            cap3.setEmail("cap3@example.com");
            User cap4 = new User("Cap4", "1234");
            cap4.setEmail("cap4@example.com");
            User cap5 = new User("Cap5", "1234");
            cap5.setEmail("cap5@example.com");
            User cap6 = new User("Cap6", "1234");
            cap6.setEmail("cap6@example.com");

            // Users
            User user1 = new User("User1", "1234");
            user1.setEmail("user1@example.com");
            User user2 = new User("User2", "1234");
            user2.setEmail("user2@example.com");
            User user3 = new User("User3", "1234");
            user3.setEmail("user3@example.com");
            User user4 = new User("User4", "1234");
            user4.setEmail("user4@example.com");
            User user5 = new User("User5", "1234");
            user5.setEmail("user5@example.com");
            User user6 = new User("User6", "1234");
            user6.setEmail("user6@example.com");

            // Player Accounts
            PlayerAccount playerAccountCap1 = new PlayerAccount("Cap1Account", LEAGUE_OF_LEGENDS, "Platinum", cap1);
            PlayerAccount playerAccountCap2 = new PlayerAccount("Cap2Account", LEAGUE_OF_LEGENDS, "Gold", cap2);
            PlayerAccount playerAccountCap3 = new PlayerAccount("Cap3Account", LEAGUE_OF_LEGENDS, "Diamond", cap3);
            PlayerAccount playerAccountCap4 = new PlayerAccount("Cap4Account", DOTA_2, "Platinum", cap4);
            PlayerAccount playerAccountCap5 = new PlayerAccount("Cap5Account", DOTA_2, "Gold", cap5);
            PlayerAccount playerAccountCap6 = new PlayerAccount("Cap6Account", DOTA_2, "Diamond", cap6);

            PlayerAccount playerAccount1 = new PlayerAccount("Mads Mikkelsen", LEAGUE_OF_LEGENDS, "Bronze", user1);
            PlayerAccount playerAccount2 = new PlayerAccount("Nikolaj Coster", LEAGUE_OF_LEGENDS, "Silver", user2);
            PlayerAccount playerAccount3 = new PlayerAccount("Pilou Asbæk", LEAGUE_OF_LEGENDS, "Gold", user3);
            PlayerAccount playerAccount4 = new PlayerAccount("Anders Matthesen", DOTA_2, "Platinum", user4);
            PlayerAccount playerAccount5 = new PlayerAccount("Mick Øgendahl", DOTA_2, "Diamond", user5);
            PlayerAccount playerAccount6 = new PlayerAccount("Jonatan Spang", DOTA_2, "Challenger", user6);

            // Teams
            Team team1 = new Team("Supra", cap1);
            Team team2 = new Team("Champions", cap2);
            Team team3 = new Team("Eagles", cap3);
            Team team4 = new Team("Brainstorm Titans", cap4);
            Team team5 = new Team("Alliance", cap5);
            Team team6 = new Team("The Caffeine Crew", cap6);

            team1.addPlayerAccount(playerAccountCap1);
            team1.addPlayerAccount(playerAccount1);
            team2.addPlayerAccount(playerAccountCap2);
            team2.addPlayerAccount(playerAccount2);
            team3.addPlayerAccount(playerAccountCap3);

            team4.addPlayerAccount(playerAccountCap4);
            team4.addPlayerAccount(playerAccount4);
            team5.addPlayerAccount(playerAccountCap5);
            team5.addPlayerAccount(playerAccount5);
            team6.addPlayerAccount(playerAccountCap6);

            // Tournaments
            Tournament tournament1 = new Tournament("League of Legends Championship", LEAGUE_OF_LEGENDS,
                    16, 5, 5000.0,
                    "Standard rules apply", "None", TournamentStatus.NOT_STARTED,
                    "2025-06-01", "10:00", "2025-06-05", "18:00", cap1);

            Tournament tournament2 = new Tournament("DOTA 2 International", DOTA_2,
                    16, 5, 10000.0, "DOTA 2 rules",
                    "Invite only", TournamentStatus.NOT_STARTED,
                    "2025-07-01", "11:00", "2025-07-10", "20:00", cap2);

            // Tournament Teams
            TournamentTeam tournamentTeam1 = new TournamentTeam("Supra", LEAGUE_OF_LEGENDS, cap1);
            tournamentTeam1.setTeam(team1);
            tournamentTeam1.setTournament(tournament1);
            tournamentTeam1.setTournamentStatus(TournamentStatus.NOT_STARTED);
            tournamentTeam1.setTournamentTeamAccounts(List.of(playerAccountCap1, playerAccount1));

            TournamentTeam tournamentTeam2 = new TournamentTeam("Champions", LEAGUE_OF_LEGENDS, cap2);
            tournamentTeam2.setTeam(team2);
            tournamentTeam2.setTournament(tournament1);
            tournamentTeam2.setTournamentStatus(TournamentStatus.NOT_STARTED);
            tournamentTeam2.setTournamentTeamAccounts(List.of(playerAccountCap2, playerAccount2));

            // Persist entities
            List<User> users = List.of(cap1, cap2, cap3, cap4, cap5, cap6, user1, user2, user3, user4, user5, user6);
            users.forEach(em::persist);

            List<PlayerAccount> playerAccounts = List.of(
                    playerAccountCap1, playerAccountCap2, playerAccountCap3,
                    playerAccountCap4, playerAccountCap5, playerAccountCap6,
                    playerAccount1, playerAccount2, playerAccount3,
                    playerAccount4, playerAccount5, playerAccount6
            );
            playerAccounts.forEach(em::persist);

            List<Team> teams = List.of(team1, team2, team3, team4, team5, team6);
            teams.forEach(em::persist);

            em.persist(tournament1);
            em.persist(tournament2);
            em.persist(tournamentTeam1);
            em.persist(tournamentTeam2);

            em.getTransaction().commit();
        }
    }



    /*public static void clearDatabase(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // delete from all tables
            em.createQuery("DELETE FROM TournamentTeam").executeUpdate();
            em.createQuery("DELETE FROM Team").executeUpdate();
            em.createQuery("DELETE FROM Tournament").executeUpdate();
            em.createQuery("DELETE FROM PlayerAccount").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();


            // reset sequences
            em.createNativeQuery("ALTER SEQUENCE player_account_player_account_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE team_team_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE tournament_tournament_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE users_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE tournament_team_tournament_team_id_seq RESTART WITH 1").executeUpdate();

            em.getTransaction().commit();
        }
    }*/
}
