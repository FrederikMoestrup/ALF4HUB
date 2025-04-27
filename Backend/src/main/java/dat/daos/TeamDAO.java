package dat.daos;

import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
    public List<TeamDTO> getAll(){
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

    @Override
    public TeamDTO update(Integer id, TeamDTO teamDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            team.setTeamName(teamDTO.getTeamName());
            team.setGame(teamDTO.getGame());

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

            em.remove(team);
            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }
    public TeamDTO invitePlayer(Integer teamId, PlayerAccountDTO playerDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Team team = em.find(Team.class, teamId);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            PlayerAccount existingPlayer = em.find(PlayerAccount.class, playerDTO.getId());
            if (existingPlayer == null) {
                throw new ApiException(404, "Player not found");
            }

            team.addPlayerAccount(existingPlayer);
            em.getTransaction().commit();

            return new TeamDTO(team);
        }
    }
}
