package dat.daos;

import dat.config.HibernateConfig;
//import dat.config.Populate;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.TournamentTeamDTO;
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

        //Populate.populateDatabase(emf);
    }

    @AfterEach
    void tearDown() {
       // Populate.clearDatabase(emf);
    }


    @Test
    void getById() throws ApiException {
        PlayerAccountDTO playerAccountDTO = playerAccountDAO.getAll().get(0);

        assertNotNull(playerAccountDTO);
        assertEquals("Cap1Account", playerAccountDTO.getPlayerAccountName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, playerAccountDTO.getGame());
        assertEquals("Platinum", playerAccountDTO.getRank());

        //user test
        assertNotNull(playerAccountDTO.getUser());
        assertEquals("Cap1", playerAccountDTO.getUser().getUsername());

        //team test
        assertNotNull(playerAccountDTO.getTeams());
        assertTrue(playerAccountDTO.getTeams().isEmpty());
        //assertEquals("Supra", playerAccountDTO.getTeams().get(0).getTeamName());

        //tournament team test
        assertNotNull(playerAccountDTO.getTournamentTeams());
        assertTrue(playerAccountDTO.getTournamentTeams().isEmpty());
        //assertEquals("Supra", playerAccountDTO.getTournamentTeams().get(0).getTournamentTeamName());
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
        assertEquals("Cap1Account", playerAccounts.get(0).getPlayerAccountName());
    }

    @Test
    void create() {
        PlayerAccountDTO dto = new PlayerAccountDTO();
        dto.setPlayerAccountName("NewTestAccount");
        dto.setGame(Game.DOTA_2);
        dto.setRank("Platinum");
        //dto.setUser(new UserDTO("NewUser", "1234"));

        PlayerAccountDTO created = playerAccountDAO.create(dto);

        assertNotNull(created);
        assertEquals("NewTestAccount", created.getPlayerAccountName());
        assertEquals(Game.DOTA_2, created.getGame());
        assertEquals("Platinum", created.getRank());
        //assertEquals("NewUser", created.getUser().getUsername());

        assertNotNull(created.getTeams());
        assertTrue(created.getTeams().isEmpty());

        assertNotNull(created.getTournamentTeams());
        assertTrue(created.getTournamentTeams().isEmpty());
    }


    @Test
    void update() throws ApiException {
        PlayerAccountDTO dto = playerAccountDAO.getById(1);
        dto.setPlayerAccountName("UpdatedTestAccount");
        dto.setGame(Game.COUNTER_STRIKE);
        dto.setRank("Diamond");
        //dto.setUser();
        //dto.setTeams(List.of(new TeamDTO("UpdatedTeam", Game.COUNTER_STRIKE, new UserDTO("UpdatedUser"))));

        PlayerAccountDTO updated = playerAccountDAO.update(dto.getId(), dto);

        assertNotNull(updated);
        assertEquals("UpdatedTestAccount", updated.getPlayerAccountName());
        assertEquals(Game.COUNTER_STRIKE, updated.getGame());
        assertEquals("Diamond", updated.getRank());
        //assertEquals("Cap1", updated.getUser().getUsername());

        assertNotNull(updated.getTeams());
        assertTrue(updated.getTeams().isEmpty());

        assertNotNull(updated.getTournamentTeams());
        assertTrue(updated.getTournamentTeams().isEmpty());
    }


    @Test
    void delete() throws ApiException {
        PlayerAccountDTO dto = playerAccountDAO.getById(1);

        assertNotNull(dto);
        assertEquals("Cap1Account", dto.getPlayerAccountName());

        playerAccountDAO.delete(dto.getId());

        List<PlayerAccountDTO> remaining = playerAccountDAO.getAll();
        assertEquals(11, remaining.size());
    }

    @Test
    void getPlayersByTeamId() throws ApiException {
        List<PlayerAccount> players = playerAccountDAO.getPlayersByTeamId(1);

        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertEquals(2, players.size());
        assertEquals("Cap1Account", players.get(0).getPlayerAccountName());
    }
}