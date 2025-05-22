import dat.config.HibernateConfig;
import dat.daos.UserDAO;
import dat.entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;

public class UserStrikeStepDefinitions {

    private User user;
    private UserDAO userDAO;

    private User user2 = new User("Test", "pass123", "test@test.mail");
    private String uploeadedPictureLink = "https://example.com/profile.jpg";

    public UserStrikeStepDefinitions() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        this.userDAO = UserDAO.getInstance(emf);
    }

    @Given("a user with username {string} exists")
    public void aUserWithUsernameExists(String username) {
        user = new User(username, "password123");
        userDAO.update(user);
    }

    @When("the user leaves a team right before tournament start")
    public void theUserLeavesTeamBeforeTournament() {
        user.addStrike();
        userDAO.update(user);
    }

    @Then("the user should have {int} strike")
    public void theUserShouldHaveStrike(Integer expectedStrikes) {
        User updatedUser = userDAO.findById(user.getId());
        Assertions.assertEquals(expectedStrikes, updatedUser.getStrikes());
    }


    @Given("I have uploaded a profile picture")
    public void iHaveUploadedAProfilePicture() {
    }

    @When("I save")
    public void iSave() {

        userDAO.updateProfilePicture(user, uploeadedPictureLink);
    }

    @Then("the profile picture should be updated and displayed correctly across the platform")
    public void theProfilePictureShouldBeUpdatedAndDisplayedCorrectlyAcrossThePlatform() {

        String actualPictureLink = userDAO.getProfilePicture(user);
        String expectedPictureLink = uploeadedPictureLink;

        Assertions.assertEquals(expectedPictureLink, actualPictureLink);
    }
}
