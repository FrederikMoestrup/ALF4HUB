package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.UserDTO;
import dat.entities.User;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        userDAO = UserDAO.getInstance(emf);
    }

    @Test
    void testGetById_returnsCorrectUser() throws ApiException {
        User user = new User("TestUser", "password123", "test@email.com");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();

        UserDTO result = userDAO.getById(user.getId());

        assertEquals("TestUser", result.getUsername());
        assertEquals("test@email.com", result.getEmail());
    }

    @Test
    void testGetById_userNotFound_throwsException() {
        ApiException exception = assertThrows(ApiException.class, () -> {
            userDAO.getById(2);
        });

        assertEquals(404, exception.getStatusCode());
        assertEquals("User not found", exception.getMessage());
    }
}
