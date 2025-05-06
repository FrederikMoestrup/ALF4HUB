package dat.daos;

import dat.dtos.TeamDTO;
import dat.entities.Team;
import dat.entities.TeamJoinRequest;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TeamJoinRequestDAO {

    private static TeamJoinRequestDAO instance;
    private static EntityManagerFactory emf;

    public static TeamJoinRequestDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeamJoinRequestDAO();
        }
        return instance;
    }

    public TeamDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }
            return new TeamDTO(team);
        }
    }


    public List<TeamDTO> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            List<Team> teams = em.createQuery("SELECT t FROM Team t", Team.class).getResultList();
            return teams.stream().map(TeamDTO::new).toList();
        }
    }


    public TeamDTO create(TeamDTO teamDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Team team = new Team(teamDTO);
            em.persist(team);
            em.getTransaction().commit();
            return new TeamDTO(team);
        }
    }


}
