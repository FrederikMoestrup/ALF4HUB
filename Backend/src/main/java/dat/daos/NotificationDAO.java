package dat.daos;

import dat.dtos.NotificationDTO;
import dat.dtos.TournamentDTO;
import dat.entities.Notification;
import dat.entities.Tournament;
import dat.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import dat.exceptions.ApiException;

import java.util.List;

public class NotificationDAO implements IDAO <NotificationDTO, Integer>
{
    private static NotificationDAO instance;
    private static EntityManagerFactory emf;

    public static NotificationDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new NotificationDAO();
        }
        return instance;
    }

    @Override
    public NotificationDTO getById(Integer id) throws ApiException{
        try (EntityManager em = emf.createEntityManager()) {
            Notification notification = em.find(Notification.class, id);
            if (notification == null) {
                throw new ApiException(404, "Notification not found");
            }
            return new NotificationDTO(notification);
        }
    }

    @Override
    public List<NotificationDTO> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            List<Notification> notifications = em.createQuery("SELECT n FROM Notification n", Notification.class).getResultList();
            return notifications.stream().map(NotificationDTO::new).toList();
        }
    }

    @Override
    public NotificationDTO create(NotificationDTO notificationDTO)
    {
        return null;
    }

    public NotificationDTO create(NotificationDTO dto, User user) {
        Notification notification = new Notification(dto, user);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(notification);
            em.getTransaction().commit();
        }
        return new NotificationDTO(notification);
    }

    @Override
    public NotificationDTO update(Integer id, NotificationDTO dto) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Notification notification = em.find(Notification.class, id);
            if (notification == null) {
                throw new ApiException(404, "Notification not found");
            }
            notification.setRead(dto.isRead());
            notification.setNotificationTitle(dto.getNotificationTitle());
            em.getTransaction().commit();
            return new NotificationDTO(notification);
        }
    }

    @Override
    public NotificationDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Notification notification = em.find(Notification.class, id);
            if (notification == null) {
                throw new ApiException(404, "Notification not found");
            }
            em.remove(notification);
            em.getTransaction().commit();
            return new NotificationDTO(notification);
        }
    }

    public long countUnreadForUser(String username) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT COUNT(n) FROM Notification n WHERE n.user.username = :username AND n.isRead = false",
                            Long.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }

    public long getNotificationCountForUser(String username) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT COUNT(n) FROM Notification n WHERE n.user.username = :username",
                            Long.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();
        }
    }


    public List<NotificationDTO> getAllForUser(String username) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Notification> list = em.createQuery(
                    "SELECT n FROM Notification n WHERE n.user.username = :username ORDER BY n.createdAt DESC",
                    Notification.class
            ).setParameter("username", username).getResultList();

            return list.stream().map(NotificationDTO::new).toList();
        }
    }

    public NotificationDTO markAsRead(int id, String username) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Notification n = em.find(Notification.class, id);

            if (n == null || !n.getUser().getUsername().equals(username)) {
                throw new ApiException(404, "Notification not found or access denied");
            }

            n.setRead(true);
            em.getTransaction().commit();
            return new NotificationDTO(n);
        }
    }

    public int markAllAsRead(String username) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<Notification> notifs = em.createQuery(
                            "SELECT n FROM Notification n WHERE n.user.username = :username AND n.isRead = false", Notification.class
                    )
                    .setParameter("username", username)
                    .getResultList();

            for (Notification n : notifs) {
                n.setRead(true);
            }

            em.getTransaction().commit();
            return notifs.size();
        }
    }


}

