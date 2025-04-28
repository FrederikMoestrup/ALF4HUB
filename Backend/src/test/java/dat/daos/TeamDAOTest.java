package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.TeamDTO;
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
        //will refactor to use populate class after dto stack overflow fix

        teamDAO = TeamDAO.getInstance(emf);

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("TestTeam");
        teamDTO.setGame(Game.LEAGUE_OF_LEGENDS);
        //teamDTO.setTeamCaptain(null);
        //teamDTO.setTournament(null);
        //teamDTO.setTeamAccounts(null);

        teamDAO.create(teamDTO);
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Team").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE team_team_id_seq RESTART WITH 1").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @Test
    void getById() throws ApiException {
        TeamDTO teamDTO = teamDAO.getById(1);

        assertNotNull(teamDTO);
        assertEquals("TestTeam", teamDTO.getTeamName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, teamDTO.getGame());
        //assertNotNull(teamDTO.getTeamCaptain());
        //assertNotNull(teamDTO.getTournament());
        //assertNotNull(teamDTO.getTeamAccounts());
    }

    @Test
    void getByIdNotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> teamDAO.getById(999));
        assertEquals(404, exception.getStatusCode());
        assertEquals("Team not found", exception.getMessage());
    }

    @Test
    void getAll() {
        TeamDTO teamDTO = teamDAO.getAll().get(0);
        assertEquals("TestTeam", teamDTO.getTeamName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, teamDTO.getGame());
    }

    @Test
    void create() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("NewTestTeam");
        teamDTO.setGame(Game.COUNTER_STRIKE);
        //teamDTO.setTeamCaptain(null);
        //teamDTO.setTournament(null);
        //teamDTO.setTeamAccounts(null);

        TeamDTO createdTeam = teamDAO.create(teamDTO);

        assertNotNull(createdTeam);
        assertEquals("NewTestTeam", createdTeam.getTeamName());
        assertEquals(Game.COUNTER_STRIKE, createdTeam.getGame());
        //assertNull(createdTeam.getTeamCaptain());
        //assertNull(createdTeam.getTournament());
        //assertNotNull(createdTeam.getTeamAccounts());
    }

    @Test
    void update() throws ApiException {
        TeamDTO teamDTO = teamDAO.getAll().get(0);
        teamDTO.setTeamName("UpdatedTestTeam");
        teamDTO.setGame(Game.COUNTER_STRIKE);
        //teamDTO.setTeamCaptain(null);
        //teamDTO.setTournament(null);
        //teamDTO.setTeamAccounts(null);

        TeamDTO updatedTeam = teamDAO.update(teamDTO.getId(), teamDTO);

        assertNotNull(updatedTeam);
        assertEquals("UpdatedTestTeam", updatedTeam.getTeamName());
        assertEquals(Game.COUNTER_STRIKE, updatedTeam.getGame());
        //assertNull(updatedTeam.getTeamCaptain());
        //assertNull(updatedTeam.getTournament());
        //assertNotNull(updatedTeam.getTeamAccounts());
    }

    @Test
    void delete() throws ApiException {
        TeamDTO teamBeforeDelete = teamDAO.getAll().get(0);

        assertNotNull(teamBeforeDelete);
        assertEquals("TestTeam", teamBeforeDelete.getTeamName());

        teamDAO.delete(teamBeforeDelete.getId());

        List<TeamDTO> teams = teamDAO.getAll();
        assertTrue(teams.isEmpty());
    }
}