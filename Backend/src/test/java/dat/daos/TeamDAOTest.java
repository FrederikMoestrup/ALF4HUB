package dat.daos;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.TournamentDTO;
import dat.dtos.UserDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
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

class TeamDAOTest {
    private static EntityManagerFactory emf;
    private TeamDAO teamDAO;
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
        teamDAO = TeamDAO.getInstance(emf);
        playerAccountDAO = PlayerAccountDAO.getInstance(emf);
        Populate.populateDatabase(emf);
    }

    @AfterEach
    void tearDown() {
        Populate.clearDatabase(emf);
    }

    @Test
    void getById() throws ApiException {
        TeamDTO teamDTO = teamDAO.getById(1);
        assertNotNull(teamDTO);
        assertEquals("Supra", teamDTO.getTeamName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, teamDTO.getGame());
        assertEquals("Cap1", teamDTO.getTeamCaptain().getUsername());
        //assertEquals("League of Legends Championship",teamDTO.getTournament().getTournamentName());
        assertEquals(2, teamDTO.getTeamAccounts().size());
    }

    @Test
    void getByIdNotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> teamDAO.getById(999));
        assertEquals(404, exception.getStatusCode());
        assertEquals("Team not found", exception.getMessage());
    }

    @Test
    void getAll() {
        List<TeamDTO> all = teamDAO.getAll();
        assertEquals(6, all.size());
    }

    @Test
    void create() throws ApiException {
        //PlayerAccountDTO captainDTO = playerAccountDAO.getById(1);
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("NewTestTeam");
        teamDTO.setGame(Game.COUNTER_STRIKE);
        //teamDTO.setTeamCaptain(captainDTO.getUser());
        //teamDTO.setTeamAccounts(List.of(captainDTO));

        TeamDTO createdTeam = teamDAO.create(teamDTO);

        assertNotNull(createdTeam);
        assertEquals("NewTestTeam", createdTeam.getTeamName());
        assertEquals(Game.COUNTER_STRIKE, createdTeam.getGame());

        //assertEquals("Cap1",createdTeam.getTeamCaptain().getUsername());
        //assertNull(createdTeam.getTournament());
        //assertEquals(1,createdTeam.getTeamAccounts().size());
        //assertEquals("Cap1Account", createdTeam.getTeamAccounts().get(0).getPlayAccountName());
    }

    @Test
    void update() throws ApiException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Team team = em.find(Team.class, 1);
        PlayerAccount newPlayer = em.find(PlayerAccount.class, 3);

        team.setTeamName("UpdatedTestTeam");
        team.setGame(Game.COUNTER_STRIKE);
        //team.setTournament(null);
        team.setTeamCaptain(newPlayer.getUser());
        team.addPlayerAccount(newPlayer);

        em.getTransaction().commit();
        em.close();

        TeamDTO updatedTeam = teamDAO.update(team.getId(), new TeamDTO(team));

        assertNotNull(updatedTeam);
        assertEquals("UpdatedTestTeam", updatedTeam.getTeamName());
        assertEquals(3, updatedTeam.getTeamAccounts().size());
        assertEquals(Game.COUNTER_STRIKE, updatedTeam.getGame());
        assertEquals("Cap3Account", updatedTeam.getTeamAccounts().get(2).getPlayAccountName());
        //assertEquals("League of Legends Championship", updatedTeam.getTournament().getTournamentName());
        assertEquals("Cap3", updatedTeam.getTeamCaptain().getUsername());
        assertEquals(3, updatedTeam.getTeamAccounts().size());
    }


    @Test
    void delete() throws ApiException {
        TeamDTO teamBeforeDelete = teamDAO.getById(1);

        assertNotNull(teamBeforeDelete);
        assertEquals("Supra", teamBeforeDelete.getTeamName());

        teamDAO.delete(teamBeforeDelete.getId());

        List<TeamDTO> teams = teamDAO.getAll();
        assertEquals(5, teams.size());
    }

    //to do add test for removeplayerfromteam
}