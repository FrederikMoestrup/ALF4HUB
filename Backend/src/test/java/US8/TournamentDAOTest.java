package US8;



import dat.config.HibernateConfig;
import dat.daos.TournamentDAO;
import dat.dtos.TournamentDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.Tournament;
import dat.entities.User;
import dat.exceptions.ApiException;
import dat.populator.Populator;
import dat.security.entities.Role;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TournamentDAOTest {

    private static Populator populator;
    private static TournamentDAO tournamentDAO;
    private static EntityManagerFactory emf;

    private List<User> users;
    private List<TournamentDTO> tournamentDTOs;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        populator = Populator.getInstance(emf);
        tournamentDAO = TournamentDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        List<Role> roles = populator.createRoles();
        users = populator.createUsers(roles);
        populator.persist(users);

        List<PlayerAccount> playerAccounts = populator.createPlayerAccounts(users);
        List<Team> teams = populator.createTeams(playerAccounts);
        populator.persist(teams);

        List<Tournament> tournaments = populator.createTournaments(users);
        populator.persist(tournaments);

        tournamentDTOs = tournaments.stream().map(TournamentDTO::new).toList();
    }

    @AfterEach
    void tearDown() {
        populator.cleanup(Tournament.class);
        populator.cleanup(Team.class);
        populator.cleanup(PlayerAccount.class);
        populator.cleanup(User.class);
        populator.cleanup(Role.class);
    }




    @Test
    void getTournamentsByPlayerUserId() throws ApiException {
        User player = users.get(5);

        List<TournamentDTO> tournaments = tournamentDAO.getTournamentsByPlayerId(player.getId());

        assertThat(tournaments, is(not(empty())));
        assertThat(tournaments, everyItem(hasProperty("players", hasItem(hasProperty("id", is(player.getId()))))));
    }
}




