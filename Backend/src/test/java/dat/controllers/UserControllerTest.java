package dat.controllers;

import dat.config.HibernateConfig;
import dat.entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private String token;
    private User user;
    private EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        RestAssured.baseURI = "http://localhost:7070/api";

        user = new User("TestUser", "password123", "test@email.com");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();

        // logind og hent token
        String loginBody = """
        {
            "username": "TestUser",
            "password": "password123"
        }
        """;

        Response loginRes = given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .post("/auth/login");

        token = loginRes.jsonPath().getString("token");
    }

    @Test
    void testGetUserById_withToken_returnsCorrectInfo() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .get("/users/" + user.getId());

        assertEquals(200, response.statusCode());
        assertEquals("TestUser", response.jsonPath().getString("username"));
        assertEquals("test@email.com", response.jsonPath().getString("email"));
    }
}
