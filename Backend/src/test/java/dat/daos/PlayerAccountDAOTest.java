package dat.daos;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.UserDTO;
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
        Populate.clearDatabase(emf);
    }


    @Test
    void getById() throws ApiException {
        PlayerAccountDTO playerAccountDTO = playerAccountDAO.getAll().get(0);

        assertNotNull(playerAccountDTO);
        assertEquals("Cap1Account", playerAccountDTO.getPlayAccountName());
        assertTrue(playerAccountDTO.isActive());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.getGame());
        assertEquals("Platinum", playerAccountDTO.getRank());

        //user test
        assertNotNull(playerAccountDTO.getUser());
        assertEquals("Cap1", playerAccountDTO.getUser().getUsername());

        //team test
        //assertNotNull(playerAccountDTO.getTeams());
        //assertFalse(playerAccountDTO.getTeams().isEmpty());
        //assertEquals("Supra", playerAccountDTO.getTeams().get(0).getTeamName());
    }

    @Test
    void getByIdNotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> playerAccountDAO.getById(999));
        assertEquals(404, exception.getStatusCode());
        assertEquals("PlayerAccount not found", exception.getMessage());
    }

    @Test
    void getAll() {
        List<PlayerAccountDTO> playerAccounts = playerAccountDAO.getAll();

        assertNotNull(playerAccounts);
        assertEquals(12, playerAccounts.size());
        assertEquals("Cap1Account", playerAccounts.get(0).getPlayAccountName());
    }

    @Test
    void create() {
        PlayerAccountDTO dto = new PlayerAccountDTO();
        dto.setPlayAccountName("NewTestAccount");
        dto.setActive(true);
        dto.setGame(Game.DOTA_2);
        dto.setRank("Platinum");
        //dto.setUser(new UserDTO("NewUser", "1234"));

        PlayerAccountDTO created = playerAccountDAO.create(dto);

        assertNotNull(created);
        assertEquals("NewTestAccount", created.getPlayAccountName());
        assertTrue(created.isActive());
        assertEquals(Game.DOTA_2, created.getGame());
        assertEquals("Platinum", created.getRank());
        //assertEquals("NewUser", created.getUser().getUsername());
        assertNull(created.getTeams());
        //assertTrue(created.getTeams().isEmpty());


        //should a newly created player have a null or empty team list?
    }


    @Test
    void update() throws ApiException {
        PlayerAccountDTO dto = playerAccountDAO.getById(1);
        dto.setPlayAccountName("UpdatedTestAccount");
        dto.setActive(false);
        dto.setGame(Game.COUNTER_STRIKE);
        dto.setRank("Diamond");
        //dto.setUser();
        //dto.setTeams(List.of(new TeamDTO("UpdatedTeam", Game.COUNTER_STRIKE, new UserDTO("UpdatedUser"))));

        PlayerAccountDTO updated = playerAccountDAO.update(dto.getId(), dto);

        assertNotNull(updated);
        assertEquals("UpdatedTestAccount", updated.getPlayAccountName());
        assertFalse(updated.isActive());
        assertEquals(Game.COUNTER_STRIKE, updated.getGame());
        assertEquals("Diamond", updated.getRank());
        //assertEquals("Cap1", updated.getUser().getUsername());
        //assertTrue(updated.getTeams().isEmpty());

        // need to also check teamaccount team_account

    }


    @Test
    void delete() throws ApiException {
        PlayerAccountDTO dto = playerAccountDAO.getById(1);

        assertNotNull(dto);
        assertEquals("Cap1Account", dto.getPlayAccountName());

        playerAccountDAO.delete(dto.getId());

        List<PlayerAccountDTO> remaining = playerAccountDAO.getAll();
        assertEquals(11, remaining.size());
    }
}