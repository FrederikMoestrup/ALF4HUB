package dat.daos;

import dat.config.HibernateConfig;

import dat.dtos.TournamentDTO;
import dat.enums.Game;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentDAOTest {
    private static EntityManagerFactory emf;
    private TournamentDAO tournamentDAO;

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
        tournamentDAO = TournamentDAO.getInstance(emf);

        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.setTournamentName("TestTournament");
        tournamentDTO.setGame(Game.LEAGUE_OF_LEGENDS);
        tournamentDTO.setTournamentSize(16);
        tournamentDTO.setTeamSize(5);
        tournamentDTO.setPrizePool(5000.0);
        tournamentDTO.setRules("Standard rules");
        tournamentDTO.setEntryRequirements("Open to all");
        tournamentDTO.setStatus("Upcoming");
        tournamentDTO.setStartDate("2025-05-01");
        tournamentDTO.setStartTime("10:00");
        tournamentDTO.setEndDate("2025-05-03");
        tournamentDTO.setEndTime("18:00");
        //tournamentDTO.setHost(null);

        tournamentDAO.create(tournamentDTO);
    }

    @AfterEach
    void tearDown() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Tournament").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE tournament_tournament_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }


    @Test
    void getById() throws ApiException {
        TournamentDTO tournamentDTO = tournamentDAO.getById(1);

        assertNotNull(tournamentDTO);
        assertEquals("TestTournament", tournamentDTO.getTournamentName());
        assertEquals(Game.LEAGUE_OF_LEGENDS, tournamentDTO.getGame());
    }

    @Test
    void getByIdNotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> tournamentDAO.getById(999));
        assertEquals(404, exception.getStatusCode());
        assertEquals("Tournament not found", exception.getMessage());
    }


    @Test
    void getAll() {
        List<TournamentDTO> tournaments = tournamentDAO.getAll();

        assertNotNull(tournaments);
        assertEquals(1, tournaments.size());
    }

    @Test
    void create() {
        TournamentDTO newTournament = new TournamentDTO();
        newTournament.setTournamentName("NewTournament");
        newTournament.setGame(Game.COUNTER_STRIKE);
        newTournament.setTournamentSize(8);
        newTournament.setTeamSize(5);
        newTournament.setPrizePool(2000.0);
        newTournament.setRules("Standard rules");
        newTournament.setEntryRequirements("Open to all");
        newTournament.setStatus("Upcoming");
        newTournament.setStartDate("2025-06-01");
        newTournament.setStartTime("10:00");
        newTournament.setEndDate("2025-06-03");
        newTournament.setEndTime("18:00");
        newTournament.setHost(null);

        TournamentDTO created = tournamentDAO.create(newTournament);

        assertNotNull(created);
        assertEquals("NewTournament", created.getTournamentName());
    }

    @Test
    void update() throws ApiException {
        TournamentDTO tournamentToUpdate = new TournamentDTO();
        tournamentToUpdate.setTournamentName("UpdatedTournament");
        tournamentToUpdate.setGame(Game.DOTA_2);
        tournamentToUpdate.setTournamentSize(8);
        tournamentToUpdate.setTeamSize(5);
        tournamentToUpdate.setPrizePool(3000.0);
        tournamentToUpdate.setRules("Updated rules");
        tournamentToUpdate.setEntryRequirements("Open to all");
        tournamentToUpdate.setStatus("Upcoming");
        tournamentToUpdate.setStartDate("2025-07-01");
        tournamentToUpdate.setStartTime("10:00");
        tournamentToUpdate.setEndDate("2025-07-03");
        tournamentToUpdate.setEndTime("18:00");
        tournamentToUpdate.setHost(null);

        TournamentDTO updated = tournamentDAO.update(1, tournamentToUpdate);

        assertNotNull(updated);
        assertEquals("UpdatedTournament", updated.getTournamentName());
    }


    @Test
    void delete() throws ApiException {
        TournamentDTO tournamentBeforeDelete = tournamentDAO.getById(1);
        assertNotNull(tournamentBeforeDelete);

        assertEquals("TestTournament", tournamentBeforeDelete.getTournamentName());
        tournamentDAO.delete(1);

        List<TournamentDTO> tournaments = tournamentDAO.getAll();
        assertTrue(tournaments.isEmpty());
    }
}