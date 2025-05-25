package US17.steps;

import dat.config.HibernateConfig;
import dat.daos.TeamDAO;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TournamentTeamDTO;
import dat.entities.*;
import dat.enums.Game;
import dat.exceptions.ApiException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TransactionRequiredException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class deleteTeamSteps {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final TeamDAO teamDAO = TeamDAO.getInstance(emf);

    private Team team;
    private int teamId;

    @Before
    public void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Opret team uden captain
            team = new Team("Test Team", null);
            em.persist(team);

            // Opret 3 player accounts med Game enum og tilknyt bruger
            List<PlayerAccount> players = List.of(
                    new PlayerAccount("player1", Game.COUNTER_STRIKE, "Gold", null),
                    new PlayerAccount("player2", Game.COUNTER_STRIKE, "Silver", null),
                    new PlayerAccount("player3", Game.COUNTER_STRIKE, "Platinum", null)
            );
            players.forEach(player -> {
                team.addPlayerAccount(player); // Tilføj spiller til team
                em.persist(player);
            });

            // Opret 3 tournament teams med Game enum og captain
            List<TournamentTeam> tournamentTeams = List.of(
                    new TournamentTeam("Tournament Team 1", Game.COUNTER_STRIKE, null),
                    new TournamentTeam("Tournament Team 2", Game.COUNTER_STRIKE, null),
                    new TournamentTeam("Tournament Team 3", Game.COUNTER_STRIKE, null)
            );
            tournamentTeams.forEach(tt -> {
                team.addTournamentTeam(tt); // Tilføj til team
                em.persist(tt);
            });

            // Gem teamets relationer (valgfrit afhængigt af hvordan dine metoder er bygget)
            team.setTeamAccounts(new ArrayList<>(players));
            team.setTournamentTeams(new ArrayList<>(tournamentTeams));

            em.getTransaction().commit();
            teamId = team.getId();
        }
    }

    @After
    public void cleanUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM TournamentTeam").executeUpdate();
            em.createQuery("DELETE FROM PlayerAccount").executeUpdate();
            em.createQuery("DELETE FROM Team").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Given("the team captain confirms the warning message")
    public void the_team_captain_confirms_the_warning_message() {
        try {
            teamDAO.delete(teamId);
        } catch (ApiException e) {
            fail("Team could not be deleted: " + e.getMessage());
        }
    }

    @Then("all players should be removed from the team")
    public void all_players_should_be_removed_from_the_team() throws ApiException {
        try{
        List<PlayerAccountDTO> players = teamDAO.getAllPlayerAccountForTeam(teamId);
        assertTrue(players.isEmpty(), "Expected no players associated with team after deletion");}
        catch (ApiException e) {
            fail("Failed to retrieve players: " + e.getMessage());
        }
    }

    @Then("all associated tournament teams should be removed from the team")
    public void all_associated_tournament_teams_should_be_removed_from_the_team() throws ApiException {
        try {
        List<TournamentTeamDTO> tournaments = teamDAO.getAllTournamentTeamsForTeam(teamId);
        assertTrue(tournaments.isEmpty(), "Expected no tournament teams associated with team after deletion");
        } catch (ApiException e) {
            fail("Failed to retrieve tournament teams: " + e.getMessage());
        }
    }

    @Then("the team should be permanently deleted from the system")
    public void the_team_should_be_permanently_deleted_from_the_system() {
        try {
            teamDAO.getById(teamId);
            fail("Team was not permanently deleted");
        } catch (ApiException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

}
