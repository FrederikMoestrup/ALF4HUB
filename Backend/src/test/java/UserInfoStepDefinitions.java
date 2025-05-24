import dat.config.HibernateConfig;
import dat.daos.UserDAO;
import dat.entities.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class UserInfoStepDefinitions {

    private User user;
    private Response response;
    private String token;

    public UserInfoStepDefinitions() {
        RestAssured.baseURI = "http://localhost:7070/api";
    }

    @Given("a user with username {string} and email {string} exists")
    public void verifyUsername(String username, String email) {

        //create user
        user = new User(username, "password123", email);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();

        // Login
        String loginBody = String.format("""
            {
              "username": "%s",
              "password": "password123"
            }
            """, username);

        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .post("/auth/login");

        Assertions.assertEquals(200, loginResponse.getStatusCode(), "Login failed");
        token = loginResponse.jsonPath().getString("token");
    }

    @When("the user requests their information")
    public void requestUserInfo() {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/" + user.getId());
    }

    @Then("the system should return username {string} and email {string}")
    public void returnUsernameAndEmail(String expectedUsername, String expectedEmail) {
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(expectedUsername, response.jsonPath().getString("username"));
        Assertions.assertEquals(expectedEmail, response.jsonPath().getString("email"));
    }
}
