package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
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
        populator.persist(users);

        List<PlayerAccount> playerAccounts = populator.createPlayerAccounts(users);
        List<Team> teams = populator.createTeams(playerAccounts);
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

        TeamDTO expected = new TeamDTO(
                "BC94",
                Game.ROCKET_LEAGUE,
                teamAccounts.get(0).getUser(),
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

    //removeplayer
    @Test
    void removePlayer() throws ApiException {
        TeamDTO team = teamDTOList.get(0);
        PlayerAccountDTO playerToRemove = team.getTeamAccounts().get(0);

        TeamDTO updatedTeam = teamDAO.removePlayer(team.getId(), playerToRemove);

        assertThat(
                updatedTeam.getTeamAccounts().stream().map(PlayerAccountDTO::getId).toList(),
                not(hasItem(playerToRemove.getId()))
        );
    }

    @Test
    void addPlayerToTeam() throws ApiException {
        TeamDTO teamDTO = teamDTOList.get(0);

        System.out.println("Before adding player:");
        teamDTO.getTeamAccounts().forEach(player -> System.out.println(" - " + player.getPlayAccountName()));

        PlayerAccountDTO newPlayer = playerAccountDTOList.stream()
                .filter(pa -> teamDTO.getTeamAccounts().stream()
                        .noneMatch(existing -> existing.getId() == pa.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No suitable player to add"));

        TeamDTO updatedTeam = teamDAO.invitePlayer(teamDTO.getId(), newPlayer);

        System.out.println("After adding player:");
        updatedTeam.getTeamAccounts().forEach(player -> System.out.println(" - " + player.getPlayAccountName()));

        assertThat(updatedTeam.getTeamAccounts(), hasSize(teamDTO.getTeamAccounts().size() + 1));
        assertThat(updatedTeam.getTeamAccounts(), hasItem(samePropertyValuesAs(newPlayer)));
    }

}