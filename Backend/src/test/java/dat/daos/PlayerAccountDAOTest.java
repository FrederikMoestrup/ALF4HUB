package dat.daos;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.dtos.PlayerAccountDTO;
import dat.entities.PlayerAccount;
import dat.entities.User;
import dat.enums.Game;
import dat.exceptions.ApiException;
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
    static void setupClass() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @BeforeAll
    static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        playerAccountDAO = PlayerAccountDAO.getInstance(emf);

        Populate.populateDatabase(emf);
    }

    @AfterEach
    void tearDown() {
        //Populate.clearDatabase(emf);
    }

    //test for stackoverflow error
    @Test
    void crashTest() {
        User user = new User();
        user.setUsername("TestUser");

        PlayerAccount playerAccount = new PlayerAccount();
        playerAccount.setPlayAccountName("TestAccount");
        playerAccount.setActive(true);
        playerAccount.setGame(Game.LEAGUE_OF_LEGENDS);
        playerAccount.setRank("Gold");

        // Link them
        playerAccount.setUser(user);
        user.setPlayerAccounts(List.of(playerAccount)); // <-- CAUSES cycle

        // Create a PlayerAccountDTO, which will trigger infinite recursion
        PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO(playerAccount);
    }

    @Test
    void getById() throws ApiException {
        PlayerAccountDTO playerAccountDTO = playerAccountDAO.getAll().get(0);

        assertNotNull(playerAccountDTO);
        assertEquals("Mads Mikkelsen", playerAccountDTO.getPlayAccountName());
        assertTrue(playerAccountDTO.isActive());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.getGame());
        assertEquals("Bronze", playerAccountDTO.getRank());
        //assertNotNull(playerAccountDTO.getUser());
        //assertFalse(playerAccountDTO.getTeams().isEmpty());
    }

    @Test
    void getByIdNotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> playerAccountDAO.getById(999));
        assertEquals(404, exception.getStatusCode());
        assertEquals("PlayerAccount not found", exception.getMessage());
    }

    @Test
    void getAll() {
        List<PlayerAccountDTO> playerAccountDTO = playerAccountDAO.getAll();

        assertNotNull(playerAccountDTO);
        assertEquals(1, playerAccountDTO.size());
        assertEquals("TestAccount", playerAccountDTO.get(0).getPlayAccountName());
        assertTrue(playerAccountDTO.get(0).isActive());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.get(0).getGame());
        assertEquals("Gold", playerAccountDTO.get(0).getRank());
        //assertNull(playerAccountDTO.get(0).getUser());
        //assertEquals(0,playerAccountDTO.get(0).getTeams().size());

    }

    @Test
    void create() {
        PlayerAccountDTO playerAccountDTO = new PlayerAccountDTO();
        playerAccountDTO.setPlayAccountName("NewTestAccount");
        playerAccountDTO.setActive(true);
        playerAccountDTO.setGame(Game.DOTA_2);
        playerAccountDTO.setRank("Platinum");
        //playerAccountDTO.setUser(null);
        //playerAccountDTO.setTeams(null);

        PlayerAccountDTO createdPlayerAccount = playerAccountDAO.create(playerAccountDTO);

        assertNotNull(createdPlayerAccount);

        assertEquals("NewTestAccount", createdPlayerAccount.getPlayAccountName());
        assertTrue(createdPlayerAccount.isActive());
        assertEquals(Game.DOTA_2, createdPlayerAccount.getGame());
        assertEquals("Platinum", createdPlayerAccount.getRank());
        //assertNull(createdPlayerAccount.getUser());
        //assertTrue(createdPlayerAccount.getTeams().isEmpty());
    }

    @Test
    void update() throws ApiException {
        PlayerAccountDTO playerAccountDTO = playerAccountDAO.getAll().get(0);
        playerAccountDTO.setPlayAccountName("UpdatedTestAccount");
        playerAccountDTO.setActive(false);
        playerAccountDTO.setGame(Game.COUNTER_STRIKE);
        playerAccountDTO.setRank("Diamond");
        //playerAccountDTO.setUser(null);
        //playerAccountDTO.setTeams(null);

        PlayerAccountDTO updatedPlayerAccount = playerAccountDAO.update(playerAccountDTO.getId(), playerAccountDTO);

        assertNotNull(updatedPlayerAccount);
        assertEquals("UpdatedTestAccount", updatedPlayerAccount.getPlayAccountName());
        assertFalse(updatedPlayerAccount.isActive());
        assertEquals(Game.COUNTER_STRIKE, updatedPlayerAccount.getGame());
        assertEquals("Diamond", updatedPlayerAccount.getRank());
        //assertNull(updatedPlayerAccount.getUser());
        //assertTrue(updatedPlayerAccount.getTeams().isEmpty());

    }

    // will lazy load
    @Test
    void delete() throws ApiException {
        PlayerAccountDTO playerAccountBeforeDelete = playerAccountDAO.getAll().get(0);

        assertNotNull(playerAccountBeforeDelete);
        assertEquals("TestAccount", playerAccountBeforeDelete.getPlayAccountName());

        playerAccountDAO.delete(playerAccountBeforeDelete.getId());

        List<PlayerAccountDTO> playerAccounts = playerAccountDAO.getAll();
        assertTrue(playerAccounts.isEmpty());
    }
}