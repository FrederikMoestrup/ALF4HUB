package dat.daos;

import dat.dtos.TournamentDTO;
import dat.entities.Tournament;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import dat.exceptions.ApiException;

import java.util.List;

public class TournamentDAO implements IDAO<TournamentDTO, Integer> {

    private static TournamentDAO instance;
    private static EntityManagerFactory emf;

    public static TournamentDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TournamentDAO();
        }
        return instance;
    }

    @Override
    public TournamentDTO getById(Integer id) throws ApiException{
        try (EntityManager em = emf.createEntityManager()) {
            Tournament tournament = em.find(Tournament.class, id);
            if (tournament == null) {
                throw new ApiException(404, "Tournament not found");
            }
            return new TournamentDTO(tournament);
        }
    }

    @Override
    public List<TournamentDTO> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            List<Tournament> tournaments = em.createQuery("SELECT t FROM Tournament t", Tournament.class).getResultList();
            return tournaments.stream().map(TournamentDTO::new).toList();
        }
    }

    @Override
    public TournamentDTO create(TournamentDTO tournamentDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Tournament tournament = new Tournament(tournamentDTO);
            em.persist(tournament);
            em.getTransaction().commit();
            return new TournamentDTO(tournament);
        }
    }

    @Override
    public TournamentDTO update(Integer id, TournamentDTO tournamentDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Tournament tournament = em.find(Tournament.class, id);
            if (tournament == null) {
                throw new ApiException(404, "Tournament not found");
            }

            tournament.setTournamentName(tournamentDTO.getTournamentName());
            tournament.setGame(tournamentDTO.getGame());
            tournament.setTournamentSize(tournamentDTO.getTournamentSize());
            tournament.setTeamSize(tournamentDTO.getTeamSize());
            tournament.setPrizePool(tournamentDTO.getPrizePool());
            tournament.setRules(tournamentDTO.getRules());
            tournament.setEntryRequirements(tournamentDTO.getEntryRequirements());
            tournament.setStatus(tournamentDTO.getStatus());
            tournament.setStartDate(tournamentDTO.getStartDate());
            tournament.setStartTime(tournamentDTO.getStartTime());
            tournament.setEndDate(tournamentDTO.getEndDate());
            tournament.setEndTime(tournamentDTO.getEndTime());

            em.getTransaction().commit();
            return new TournamentDTO(tournament);
        }
    }


    @Override
    public TournamentDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Tournament tournament = em.find(Tournament.class, id);
            if (tournament == null) {
                throw new ApiException(404, "Tournament not found");
            }

            tournament.getTeams().size();
            em.remove(tournament);
            em.getTransaction().commit();
            return new TournamentDTO(tournament);
        }
    }






}
