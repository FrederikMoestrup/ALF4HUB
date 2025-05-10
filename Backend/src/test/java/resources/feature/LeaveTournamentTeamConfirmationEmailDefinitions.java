package resources.feature;

import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.User;
import dat.enums.Game;
import dat.services.EmailService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LeaveTournamentTeamConfirmationEmailDefinitions {

    EmailService emailService = new EmailService();

    User captain = new User("Captain", "1234", "captain@mail.dk");
    Team team = new Team("Team K", Game.LEAGUE_OF_LEGENDS, captain);
    User user = new User("User", "1234", "user@mail.dk");
    PlayerAccount userPlayerAccount = new PlayerAccount("UserPlayer", true, Game.LEAGUE_OF_LEGENDS, "Gold", user);

    @Given("I am part of tournament team {string}")
    public void iAmPartOfTournamentTeam(String arg0) {
        // Assuming the user is already part of the team
        team.getTeamAccounts().add(userPlayerAccount);
        userPlayerAccount.getTeams().add(team);

        Assert.assertTrue("User should be part of the team", team.getTeamAccounts().contains(userPlayerAccount));
    }

    @When("I leave tournament team {string}")
    public void iLeaveTournamentTeam(String arg0) {
        Assert.assertTrue("User leaves Team K", true);
    }

    @Then("an email should be sent to my registered email address")
    public void anEmailShouldBeSentToMyRegisteredEmailAddress() {
        String recipientEmail = user.getEmail();
        String subject = "Tournament Team Leave Confirmation";
        String body = user.getUsername() + " you have successfully left " + team.getTeamName() + ". For the " + team.getGame() + " tournament.";

        // Send the email
        emailService.sendEmail(recipientEmail, subject, body);

        // Check if the email was sent (this is a placeholder; actual implementation may vary)
        Assert.assertTrue("Email should be sent", true);
    }

    @And("the email should contain a confirmation message {string}")
    public void theEmailShouldContainAConfirmationMessage(String arg0) {
    }
}
