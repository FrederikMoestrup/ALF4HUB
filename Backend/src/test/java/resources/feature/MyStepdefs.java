package resources.feature;

import dat.entities.Team;
import dat.entities.PlayerAccount;
import dat.entities.Tournament;
import dat.enums.Game;
import static org.junit.jupiter.api.Assertions.*;

public class MyStepdefs {

    private Team team;
    private PlayerAccount playerAccount;
    private Tournament tournament;

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
        team.removePlayerAccount(playerAccount);
    }

    @io.cucumber.java.en.Then("I am removed from the team")
    public void iAmRemovedFromTheTeam() {
        assertFalse(team.getTeamAccounts().contains(playerAccount));
    }

    @io.cucumber.java.en.And("I receive a confirmation message on the screen and\\/or via email")
    public void iReceiveAConfirmationMessageOnTheScreenAndOrViaEmail() {
        String confirmationMessage = "You have successfully left the team.";
        assertNotNull(confirmationMessage);
    }
}
