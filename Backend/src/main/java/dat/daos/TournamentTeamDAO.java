package dat.daos;

import dat.dtos.TournamentDTO;
import dat.dtos.TournamentTeamDTO;
import dat.entities.Team;
import dat.entities.Tournament;
import dat.entities.TournamentTeam;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TournamentTeamDAO implements IDAO<TournamentTeamDTO, Integer> {

    private static TournamentTeamDAO instance;
    private static EntityManagerFactory emf;

    public static TournamentTeamDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TournamentTeamDAO();
        }
        return instance;
    }

    @Override
    public TournamentTeamDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            TournamentTeam tournamentTeam = em.find(TournamentTeam.class, id);
            if (tournamentTeam == null) {
                throw new ApiException(404, "TournamentTeam not found");
            }
            return new TournamentTeamDTO(tournamentTeam);
        }
    }

    @Override
    public List<TournamentTeamDTO> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            List<TournamentTeam> tournamentTeams = em.createQuery("SELECT t FROM TournamentTeam t", TournamentTeam.class).getResultList();
            return tournamentTeams.stream().map(TournamentTeamDTO::new).toList();
        }
    }

    @Override
    public TournamentTeamDTO create(TournamentTeamDTO tournamentTeamDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TournamentTeam tournamentTeam = new TournamentTeam(tournamentTeamDTO);
            em.persist(tournamentTeam);
            em.getTransaction().commit();
            return new TournamentTeamDTO(tournamentTeam);
        }
    }

    @Override
    public TournamentTeamDTO update(Integer id, TournamentTeamDTO tournamentTeamDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TournamentTeam tournamentTeam = em.find(TournamentTeam.class, id);
            if (tournamentTeam == null) {
                throw new ApiException(404, "TournamentTeam not found");
            }

            tournamentTeam.setTournamentTeamName(tournamentTeamDTO.getTournamentTeamName());
            tournamentTeam.setGame(tournamentTeamDTO.getGame());
            tournamentTeam.setTournamentStatus(tournamentTeamDTO.getTournamentStatus());

            em.getTransaction().commit();
            return new TournamentTeamDTO(tournamentTeam);
        }
    }

    @Override
    public TournamentTeamDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TournamentTeam tournamentTeam = em.find(TournamentTeam.class, id);
            if (tournamentTeam == null) {
                throw new ApiException(404, "TournamentTeam not found");
            }

            em.remove(tournamentTeam);
            em.getTransaction().commit();
            return new TournamentTeamDTO(tournamentTeam);
        }
    }
}
