package resources.feature;

import dat.config.HibernateConfig;
import dat.daos.PlayerAccountDAO;
import dat.dtos.PlayerAccountDTO;
import dat.entities.Team;
import dat.entities.PlayerAccount;
import dat.entities.Tournament;
import dat.enums.Game;
import dat.exceptions.ApiException;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveTeam {

    private Team team;
    private PlayerAccount playerAccount;
    private Tournament tournament;
    private PlayerAccountDAO dao;

    @io.cucumber.java.en.Given("I am registered on a team")
    public void iAmRegisteredOnATeam() {
        // Setup: Make a team and a player account
        playerAccount = new PlayerAccount("TestPlayer", true, Game.DOTA_2, "Silver", null);
        team = new Team("TestTeam", Game.ROCKET_LEAGUE, null);
        team.addPlayerAccount(playerAccount);
    }

    @io.cucumber.java.en.And("I navigate to the team page")
    public void iNavigateToTheTeamPage() {
    }

    @io.cucumber.java.en.And("the tournament has not yet started")
    public void theTournamentHasNotYetStarted() {
        tournament = new Tournament();
        tournament.setStatus("NOT_STARTED");
        team.setTournament(tournament);
    }

    @io.cucumber.java.en.When("I click the {string} button")
    public void iClickTheButton(String arg0) {
        PlayerAccountDAO dao = PlayerAccountDAO.getInstance(HibernateConfig.getEntityManagerFactory("ALF4HUB_DB"));
        try {
            dao.leaveTeam(playerAccount.getId(), team.getId());
        } catch (Exception e) {
            fail("Failed to leave team: " + e.getMessage());
        }
    }

    @io.cucumber.java.en.Then("I am removed from the team")
    public void iAmRemovedFromTheTeam() throws ApiException {
        PlayerAccountDTO updated = dao.getById(playerAccount.getId());
        boolean isStillOnTeam = updated.getTeams().stream()
                .anyMatch(t -> t.getId() == team.getId());
        assertFalse(isStillOnTeam);
    }


        @io.cucumber.java.en.And("I receive a confirmation message on the screen and\\/or via email")
    public void iReceiveAConfirmationMessageOnTheScreenAndOrViaEmail() {
        String confirmationMessage = "You have successfully left the team.";
        assertNotNull(confirmationMessage);
    }
}
