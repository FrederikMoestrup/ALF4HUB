package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.enums.Game;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamDAOTest
{
    private static EntityManagerFactory emf;
    private TeamDAO teamDAO;

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
        teamDAO = TeamDAO.getInstance(emf);

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("TestTeam");
        teamDTO.setGame(Game.LEAGUE_OF_LEGENDS);
        teamDTO.setTeamCaptain(null);
        teamDTO.setTournament(null);
        teamDTO.setTeamAccounts(null);

        teamDAO.create(teamDTO);

    }

    @AfterEach
    void tearDown()
    {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Team").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE team_team_id_seq RESTART WITH 1").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @Test
    void getById() throws ApiException
    {
        TeamDTO teamDTO = teamDAO.getById(1);
        assertEquals("TestTeam", teamDTO.getTeamName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, teamDTO.getGame());
    }

    @Test
    void getAll()
    {
        TeamDTO teamDTO = teamDAO.getAll().get(0);
        assertEquals("TestTeam", teamDTO.getTeamName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, teamDTO.getGame());
    }

    @Test
    void create()
    {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamName("NewTestTeam");
        teamDTO.setGame(Game.COUNTER_STRIKE);
        teamDTO.setTeamCaptain(null);
        teamDTO.setTournament(null);
        teamDTO.setTeamAccounts(null);

        TeamDTO createdTeam = teamDAO.create(teamDTO);

        assertNotNull(createdTeam);
        assertEquals("NewTestTeam", createdTeam.getTeamName());
        assertEquals(Game.COUNTER_STRIKE, createdTeam.getGame());
    }

    @Test
    void update() throws ApiException
    {
        TeamDTO teamDTO = teamDAO.getById(1);
        teamDTO.setTeamName("UpdatedTestTeam");
        teamDTO.setGame(Game.COUNTER_STRIKE);

        TeamDTO updatedTeam = teamDAO.update(1,teamDTO);

        assertNotNull(updatedTeam);
        assertEquals("UpdatedTestTeam", updatedTeam.getTeamName());
        assertEquals(Game.COUNTER_STRIKE, updatedTeam.getGame());
    }

    @Test
    void delete()
    {
    }
}