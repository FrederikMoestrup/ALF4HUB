package dat.daos;

import dat.dtos.TeamDTO;
import dat.dtos.TeamJoinRequestDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.entities.TeamJoinRequest;
import dat.entities.User;
import dat.enums.JoinRequestStatus;
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

    public TeamJoinRequestDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            TeamJoinRequest teamJoinRequest = em.find(TeamJoinRequest.class, id);
            if (teamJoinRequest == null) {
                throw new ApiException(404, "TeamJoinRequest not found");
            }
            return new TeamJoinRequestDTO(teamJoinRequest);
        }
    }


    public List<TeamJoinRequestDTO> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            List<TeamJoinRequest> teamJoinRequests = em.createQuery("SELECT t FROM TeamJoinRequest t", TeamJoinRequest.class).getResultList();
            return teamJoinRequests.stream().map(TeamJoinRequestDTO::new).toList();
        }
    }


    public TeamJoinRequestDTO create(int requesterId, int teamId, int playerAccountId) throws ApiException{
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            User requester = em.find(User.class, requesterId);
            if (requester == null) {
                throw new ApiException(404, "Requester not found");
            }

            Team team = em.find(Team.class, teamId);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }

            PlayerAccount playerAccount = em.find(PlayerAccount.class, playerAccountId);
            if (playerAccount == null) {
                throw new ApiException(404, "PlayerAccount not found");
            }

            if (playerAccount.getTeams().contains(team)) {
                throw new ApiException(400, "You are already a member of this team.");
            }

            TeamJoinRequest teamJoinRequest = new TeamJoinRequest(requester, team, playerAccount);

            em.persist(teamJoinRequest);
            em.getTransaction().commit();
            return new TeamJoinRequestDTO(teamJoinRequest);
        }
    }

    public TeamJoinRequestDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TeamJoinRequest teamJoinRequest = em.find(TeamJoinRequest.class, id);
            if (teamJoinRequest == null) {
                throw new ApiException(404, "TeamJoinRequest not found");
            }

            em.remove(teamJoinRequest);
            em.getTransaction().commit();
            return new TeamJoinRequestDTO(teamJoinRequest);
        }
    }

    public TeamJoinRequestDTO respondToRequest(Integer id, Integer userId, JoinRequestStatus status) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TeamJoinRequest teamJoinRequest = em.find(TeamJoinRequest.class, id);
            if (teamJoinRequest == null) {
                throw new ApiException(404, "TeamJoinRequest not found");
            }

            if (teamJoinRequest.getStatus() != JoinRequestStatus.PENDING) {
                throw new ApiException(400, "TeamJoinRequest is not pending");
            }

            if (teamJoinRequest.getReceiver().getId() != userId) {
                throw new ApiException(403, "User is not authorized to respond to this request");
            }

            teamJoinRequest.setStatus(status);

            if (status == JoinRequestStatus.APPROVED) {
                teamJoinRequest.getTeam().addPlayerAccount(teamJoinRequest.getPlayerAccount());
            }

            em.getTransaction().commit();
            return new TeamJoinRequestDTO(teamJoinRequest);
        }

    }

    public List<TeamJoinRequestDTO> getRequestsForTeam(int teamId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<TeamJoinRequest> requests = em.createQuery(
                            "SELECT r FROM TeamJoinRequest r WHERE r.team.id = :teamId", TeamJoinRequest.class)
                    .setParameter("teamId", teamId)
                    .getResultList();

            return requests.stream().map(TeamJoinRequestDTO::new).toList();
        }
    }

    public List<TeamJoinRequestDTO> getRequestsForTeamCaptain(int teamCaptainId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<TeamJoinRequest> requests = em.createQuery(
                            "SELECT r FROM TeamJoinRequest r WHERE r.team.teamCaptain.id = :captainId", TeamJoinRequest.class)
                    .setParameter("captainId", teamCaptainId)
                    .getResultList();

            return requests.stream().map(TeamJoinRequestDTO::new).toList();
        }
    }



}
