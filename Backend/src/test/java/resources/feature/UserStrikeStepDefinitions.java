package resources.feature;

import dat.config.HibernateConfig;
import dat.daos.UserDAO;
import dat.entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;

public class UserStrikeStepDefinitions {
/*
    private User user;
    private UserDAO userDAO;

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

 */
}
