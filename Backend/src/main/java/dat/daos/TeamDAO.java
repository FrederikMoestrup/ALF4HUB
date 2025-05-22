package dat.daos;

import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.TournamentTeamDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.TournamentTeam;
import dat.entities.User;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements IDAO<TeamDTO, Integer> {

    private static TeamDAO instance;
    private static EntityManagerFactory emf;

    public static TeamDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeamDAO();
        }
        return instance;
    }

    @Override
    public TeamDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }
            return new TeamDTO(team);
        }
    }

    @Override
    public List<TeamDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Team> teams = em.createQuery("SELECT t FROM Team t", Team.class).getResultList();
            return teams.stream().map(TeamDTO::new).toList();
        }
    }

    @Override
    public TeamDTO create(TeamDTO teamDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = new Team(teamDTO);
            em.persist(team);
            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }

    public TeamDTO create(TeamDTO teamDTO, int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = new Team(teamDTO);
            team.setTeamCaptain(em.find(User.class, id));
            em.persist(team);
            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }

    @Override
    public TeamDTO update(Integer id, TeamDTO teamDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            team.setTeamName(teamDTO.getTeamName());

            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }

    @Override
    public TeamDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }
            // Remove all associations with PlayerAccounts
            for (PlayerAccount playerAccount : new ArrayList<>(team.getTeamAccounts())) {
                team.removePlayerAccount(playerAccount);
            }
            // Remove all associations with TournamentTeams
            for (TournamentTeam tournamentTeam : new ArrayList<>(team.getTournamentTeams())) {
                team.removeTournamentTeam(tournamentTeam);
            }
            // Remove the team
            em.remove(team);
            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }

    public boolean teamNameAlreadyExist(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT COUNT(t) FROM Team t WHERE LOWER(t.teamName) = LOWER(:name)";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("name", name.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    public TeamDTO invitePlayer(Integer teamId, Integer playerAccountId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Team team = em.find(Team.class, teamId);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            PlayerAccount playerAccount = em.find(PlayerAccount.class, playerAccountId);
            if (playerAccount == null) {
                throw new ApiException(404, "PlayerAccount not found");
            }

            team.addPlayerAccount(playerAccount);

            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }


    //Ift. spilleren får en email notifikation, skal email være en del af enten user eller playeraccount entititer
    public TeamDTO removePlayer(Integer teamId, Integer playerAccountId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Team team = em.find(Team.class, teamId);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            PlayerAccount existingPlayer = em.find(PlayerAccount.class, playerAccountId);
            if (existingPlayer == null) {
                throw new ApiException(404, "Player not found");
            }

            team.removePlayerAccount(existingPlayer);
            em.getTransaction().commit();

            //email notifikation system/metode

            return new TeamDTO(team);
        }
    }

    public List<PlayerAccountDTO> getAllPlayerAccountForTeam(int teamId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            List<PlayerAccount> playerAccounts = em.createQuery("SELECT p FROM PlayerAccount p JOIN p.teams t WHERE t.id = :teamId", PlayerAccount.class)
                    .setParameter("teamId", teamId)
                    .getResultList();
            return playerAccounts.stream().map(PlayerAccountDTO::new).toList();
        }
    }

    public List<TournamentTeamDTO> getAllTournamentTeamsForTeam(int teamId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            List<TournamentTeam> tournamentTeams = em.createQuery("SELECT tt FROM TournamentTeam tt JOIN tt.team t WHERE t.id = :teamId", TournamentTeam.class)
                    .setParameter("teamId", teamId)
                    .getResultList();
            return tournamentTeams.stream().map(TournamentTeamDTO::new).toList();
        }
    }

}
