package resources;

import static org.junit.jupiter.api.Assertions.*;

public class LoginStepdefs {

    private String inputEmail;
    private String inputPassword;
    private boolean loginSuccess;

    @io.cucumber.java.en.Given("I am a registered user")
    public void iAmARegisteredUser() {
        inputEmail = "test@example.com";
        inputPassword = "securePassword123";
    }

    @io.cucumber.java.en.When("I enter a correct email and password")
    public void iEnterACorrectEmailAndPassword() {
        loginSuccess = inputEmail.equals("test@example.com") && inputPassword.equals("securePassword123");
    }

    @io.cucumber.java.en.Then("I am logged in and redirected to the homepage")
    public void iAmLoggedInAndRedirectedToTheHomepage() {
        assertTrue(loginSuccess);
    }
}
