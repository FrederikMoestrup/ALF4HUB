package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.UserDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.User;
import dat.enums.Game;
import dat.exceptions.ApiException;
import dat.populator.Populator;
import dat.security.entities.Role;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class TeamDAOTest {

    private static Populator populator;
    private static TeamDAO teamDAO;

    private List<PlayerAccountDTO> playerAccountDTOList;
    private List<TeamDTO> teamDTOList;

    @BeforeAll
    static void beforeAll() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

        populator = Populator.getInstance(emf);
        teamDAO = TeamDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        List<Role> roles = populator.createRoles();
        List<User> users = populator.createUsers(roles);

        List<PlayerAccount> playerAccounts = populator.createPlayerAccounts(users);
        List<Team> teams = populator.createTeams(roles, playerAccounts);

        populator.persist(users);
        populator.persist(teams);

        playerAccountDTOList = playerAccounts.stream().map(PlayerAccountDTO::new).toList();
        teamDTOList = teams.stream().map(TeamDTO::new).toList();
    }

    @AfterEach
    void tearDown() {
        populator.cleanup(Team.class);
        populator.cleanup(PlayerAccount.class);
        populator.cleanup(User.class);
        populator.cleanup(Role.class);
    }

    @Test
    void getById() throws ApiException {
        TeamDTO expected = teamDTOList.get(0);
        TeamDTO actual = teamDAO.getById(expected.getId());

        assertThat(actual, is(expected));
    }

    @Test
    void getAll() {
        List<TeamDTO> expected = teamDTOList;
        List<TeamDTO> actual = teamDAO.getAll();

        assertThat(actual, hasSize(expected.size()));
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void create() {
        List<PlayerAccountDTO> teamAccounts = playerAccountDTOList.subList(19, 22);
        UserDTO teamCaptain = teamAccounts.get(0).getUser();

        teamCaptain.getRoles().add("team_captain");

        TeamDTO expected = new TeamDTO(
                "BC94",
                Game.ROCKET_LEAGUE,
                teamCaptain,
                null,
                teamAccounts
        );

        TeamDTO actual = teamDAO.create(expected);

        assertThat(actual.getId(), is(not(0)));
        assertThat(actual.getTeamAccounts(), hasSize(expected.getTeamAccounts().size()));

        for (int i = 0; i < expected.getTeamAccounts().size(); i++) {
            PlayerAccountDTO expectedPlayerAccountDTO = expected.getTeamAccounts().get(i);
            PlayerAccountDTO actualPlayerAccountDTO = actual.getTeamAccounts().get(i);

            assertThat(actualPlayerAccountDTO.getId(), is(not(0)));
            assertThat(actualPlayerAccountDTO, is(samePropertyValuesAs(expectedPlayerAccountDTO, "id")));
        }

        assertThat(actual, is(samePropertyValuesAs(expected, "id", "teamAccounts")));
    }

    @Test
    void update() throws ApiException {
        TeamDTO expected = teamDTOList.get(0);

        expected.setTeamName("Updated Team Name");

        TeamDTO actual = teamDAO.update(expected.getId(), expected);

        assertThat(actual, is(expected));
    }

    @Test
    void delete() throws ApiException {
        TeamDTO teamDTO = teamDTOList.get(0);

        teamDAO.delete(teamDTO.getId());

        assertThrowsExactly(ApiException.class, () -> teamDAO.getById(teamDTO.getId()));
    }
}