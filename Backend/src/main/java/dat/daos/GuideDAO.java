package dat.daos;

import dat.dtos.GuideDTO;
import dat.dtos.GuideTotalPriceDTO;
import dat.entities.Guide;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO, Integer> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }

    @Override
    public GuideDTO getById(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, integer);
            return new GuideDTO(guide);
        }
    }

    @Override
    public List<GuideDTO> getAll() {
        return null;
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        return null;
    }

    @Override
    public GuideDTO update(Integer integer, GuideDTO guideDTO) {
        return null;
    }

    @Override
    public GuideDTO delete(Integer integer) {
        return null;
    }

    public GuideDTO getGuideByTrip(int tripId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.createQuery("SELECT g FROM Guide g JOIN g.trips t WHERE t.id = :tripId", Guide.class)
                    .setParameter("tripId", tripId)
                    .getSingleResult();
            return new GuideDTO(guide);
        } catch (NoResultException e) {
            throw new ApiException(404, "Guide not found. Please assign a guide to this trip.");
        }
    }

    public List<GuideTotalPriceDTO> getTotalPriceByGuide() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT new dat.dtos.GuideTotalPriceDTO(g.id, SUM(t.price)) FROM Guide g JOIN g.trips t GROUP BY g.id ORDER BY g.id", GuideTotalPriceDTO.class)
                    .getResultList();
        }
    }
}
