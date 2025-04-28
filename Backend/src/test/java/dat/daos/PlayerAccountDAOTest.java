package dat.daos;
import dat.config.HibernateConfig;
import dat.dtos.PlayerAccountDTO;
import dat.enums.Game;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PlayerAccountDAOTest {
    private static EntityManagerFactory emf;
    private PlayerAccountDAO playerAccountDAO;

    @BeforeAll
    static void setupClass()
    {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @BeforeAll
    static void tearDownClass()
    {
        if (emf != null)
        {
            emf.close();
        }
    }

    @BeforeEach
    void setUp()
    {
        playerAccountDAO = playerAccountDAO.getInstance(emf);

        PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO();
        playerAccountDTO.setPlayAccountName("TestAccount");
        playerAccountDTO.setActive(true);
        playerAccountDTO.setGame(Game.LEAGUE_OF_LEGENDS);
        playerAccountDTO.setRank("Gold");
        playerAccountDTO.setUser(null);
        playerAccountDTO.setTeams(null);
        playerAccountDAO.create(playerAccountDTO);

    }

    @AfterEach
    void tearDown()
    {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PlayerAccount").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE player_account_player_account_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void getById() throws ApiException
    {
        PlayerAccountDTO playerAccountDTO = playerAccountDAO.getById(1);

        assertNotNull(playerAccountDTO);
        assertEquals("TestAccount", playerAccountDTO.getPlayAccountName());
        assertTrue(playerAccountDTO.isActive());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.getGame());
        assertEquals("Gold", playerAccountDTO.getRank());
        assertNull(playerAccountDTO.getUser());
        assertTrue(playerAccountDTO.getTeams().isEmpty());

    }

    @Test
    void getAll()
    {
        List<PlayerAccountDTO> playerAccountDTO = playerAccountDAO.getAll();

        assertNotNull(playerAccountDTO);
        assertEquals(1, playerAccountDTO.size());
        assertEquals("TestAccount", playerAccountDTO.get(0).getPlayAccountName());
        assertTrue(playerAccountDTO.get(0).isActive());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.get(0).getGame());
        assertEquals("Gold", playerAccountDTO.get(0).getRank());
        assertNull(playerAccountDTO.get(0).getUser());
        //assertTrue(playerAccountDTO.getTeams());
        assertTrue(playerAccountDTO.get(0).getTeams().isEmpty());
    }

    @Test
    void create()
    {
        PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO();
        playerAccountDTO.setPlayAccountName("NewTestAccount");
        playerAccountDTO.setActive(true);
        playerAccountDTO.setGame(Game.DOTA_2);
        playerAccountDTO.setRank("Platinum");
        playerAccountDTO.setUser(null);
        playerAccountDTO.setTeams(null);

        PlayerAccountDTO createdPlayerAccount = playerAccountDAO.create(playerAccountDTO);

        assertNotNull(createdPlayerAccount);

        assertEquals("NewTestAccount", createdPlayerAccount.getPlayAccountName());
        assertTrue(createdPlayerAccount.isActive());
        assertEquals(Game.DOTA_2, createdPlayerAccount.getGame());
        assertEquals("Platinum", createdPlayerAccount.getRank());
        assertNull(createdPlayerAccount.getUser());
        //assertTrue(createdPlayerAccount.getTeams().isEmpty());
    }

    @Test
    void update() throws ApiException
    {
        PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO();
        playerAccountDTO.setPlayAccountName("UpdatedTestAccount");
        playerAccountDTO.setActive(false);
        playerAccountDTO.setGame(Game.COUNTER_STRIKE);
        playerAccountDTO.setRank("Diamond");
        playerAccountDTO.setUser(null);
        playerAccountDTO.setTeams(null);

        PlayerAccountDTO updatedPlayerAccount = playerAccountDAO.update(1, playerAccountDTO);

        assertNotNull(updatedPlayerAccount);
        assertEquals("UpdatedTestAccount", updatedPlayerAccount.getPlayAccountName());
        assertFalse(updatedPlayerAccount.isActive());
        assertEquals(Game.COUNTER_STRIKE, updatedPlayerAccount.getGame());
        assertEquals("Diamond", updatedPlayerAccount.getRank());
        assertNull(updatedPlayerAccount.getUser());

    }

    @Test
    void delete()
    {
    }
}