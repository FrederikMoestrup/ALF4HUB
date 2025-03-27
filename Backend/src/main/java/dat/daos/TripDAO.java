package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TripDAO implements IDAO<TripDTO, Integer>,  ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public TripDTO getById(Integer id) throws ApiException{
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if(trip == null) {
                throw new ApiException(404, "Trip not found");
            }
            return new TripDTO(trip);
        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<TripDTO> tripDTOS = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t", TripDTO.class).getResultList();
            return tripDTOS;
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(Integer id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                trip.setName(tripDTO.getName());
                trip.setCategory(tripDTO.getCategory());
                trip.setPrice(tripDTO.getPrice());
                trip.setStarttime(tripDTO.getStarttime());
                trip.setEndtime(tripDTO.getEndtime());
                trip.setStartposition(tripDTO.getStartposition());
            }
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO delete(Integer integer) throws ApiException{
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                throw new ApiException(404, "Trip not found");
            }
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            guide.addTrip(trip);
            Guide guideMerged = em.merge(guide);
            Trip tripWithNewGuide = em.find(Trip.class, tripId);
            em.getTransaction().commit();
            return new TripDTO(tripWithNewGuide);
        }
    }

    @Override
    public List<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<TripDTO> tripDTOS = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.guide.id = :guideId ORDER BY t.id", TripDTO.class)
                    .setParameter("guideId", guideId)
                    .getResultList();
            return tripDTOS;
        }
    }

    public void populate() {
        Populate.populate();
    }

    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null;
        }
    }

    public boolean validateGuidePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return guide != null;
        }
    }

    public List<TripDTO> getTripsByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            List<TripDTO> tripDTOS = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.category = :category", TripDTO.class)
                    .setParameter("category", category)
                    .getResultList();
            return tripDTOS;
        }
    }
}
