package dat.daos;

import dat.config.HibernateConfig;

import dat.config.Populate;
import dat.dtos.TournamentDTO;
import dat.enums.Game;
import dat.enums.TournamentStatus;
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
        tournamentDAO = TournamentDAO.getInstance(emf);

        Populate.populateDatabase(emf);
    }

    @AfterEach
    void tearDown() {
        Populate.clearDatabase(emf);
    }


    @Test
    void getById() throws ApiException {
        TournamentDTO tournamentDTO = tournamentDAO.getById(1);

        assertNotNull(tournamentDTO);
        assertEquals("League of Legends Championship", tournamentDTO.getTournamentName());
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
        assertEquals(2, tournaments.size());
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
        newTournament.setTournamentStatus(TournamentStatus.NOT_STARTED);
        newTournament.setStartDate("2025-06-01");
        newTournament.setStartTime("10:00");
        newTournament.setEndDate("2025-06-03");
        newTournament.setEndTime("18:00");
        //newTournament.setHost();

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
        tournamentToUpdate.setTournamentStatus(TournamentStatus.NOT_STARTED);
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
        assertEquals("League of Legends Championship", tournamentBeforeDelete.getTournamentName());

        tournamentDAO.delete(tournamentBeforeDelete.getId());

        List<TournamentDTO> tournaments = tournamentDAO.getAll();
        assertEquals(1, tournaments.size());
    }
}