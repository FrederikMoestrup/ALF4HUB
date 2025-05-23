package dat.daos.teamh;

import dat.config.HibernateConfig;
import dat.daos.NotificationDAO;
import dat.dtos.NotificationDTO;
import dat.entities.User;
import dat.enums.NotificationType;
import dat.exceptions.ApiException;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationDAOTest {

    private EntityManagerFactory emf;
    private NotificationDAO notificationDAO;

    private User testUser;

    @BeforeAll
    void setupClass() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        notificationDAO = NotificationDAO.getInstance(emf);

        testUser = new User();
        testUser.setUsername("lars");
        testUser.setPassword("test123");
        testUser.setEmail("lars@example.com");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testUser);
            em.getTransaction().commit();
        }
    }

    @AfterAll
    void tearDown() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    void cleanNotifications() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Notification").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void testCreateNotification() {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.INFO);
        dto.setNotificationTitle("Test notification");
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());

        NotificationDTO created = notificationDAO.createNotification(dto, testUser);
        assertTrue(created.getId() > 0, "Id should be positive");
        assertEquals("Test notification", created.getNotificationTitle());
        assertFalse(created.isRead());
    }

    @Test
    void testGetById() throws ApiException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.INFO);
        dto.setNotificationTitle("Find me");
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());

        NotificationDTO created = notificationDAO.createNotification(dto, testUser);

        NotificationDTO found = notificationDAO.getById(created.getId());
        assertEquals(created.getNotificationTitle(), found.getNotificationTitle());
    }

    @Test
    void testUpdate() throws ApiException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.INFO);
        dto.setNotificationTitle("Update me");
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());

        NotificationDTO created = notificationDAO.createNotification(dto, testUser);

        created.setRead(true);
        created.setNotificationTitle("Updated title");
        NotificationDTO updated = notificationDAO.update(created.getId(), created);

        assertTrue(updated.isRead());
        assertEquals("Updated title", updated.getNotificationTitle());
    }

    @Test
    void testDelete() throws ApiException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.INFO);
        dto.setNotificationTitle("Delete me");
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());

        NotificationDTO created = notificationDAO.createNotification(dto, testUser);
        NotificationDTO deleted = notificationDAO.delete(created.getId());

        assertEquals(created.getId(), deleted.getId());

        assertThrows(ApiException.class, () -> notificationDAO.getById(created.getId()));
    }

    @Test
    void testCountUnreadForUser() {
        NotificationDTO dto1 = new NotificationDTO();
        dto1.setNotificationType(NotificationType.INFO);
        dto1.setNotificationTitle("Unread 1");
        dto1.setRead(false);
        dto1.setCreatedAt(LocalDateTime.now());

        NotificationDTO dto2 = new NotificationDTO();
        dto2.setNotificationType(NotificationType.INFO);
        dto2.setNotificationTitle("Unread 2");
        dto2.setRead(false);
        dto2.setCreatedAt(LocalDateTime.now());

        NotificationDTO dto3 = new NotificationDTO();
        dto3.setNotificationType(NotificationType.INFO);
        dto3.setNotificationTitle("Read");
        dto3.setRead(true);
        dto3.setCreatedAt(LocalDateTime.now());

        notificationDAO.createNotification(dto1, testUser);
        notificationDAO.createNotification(dto2, testUser);
        notificationDAO.createNotification(dto3, testUser);

        long unreadCount = notificationDAO.countUnreadForUser(testUser.getUsername());
        assertEquals(2, unreadCount);

        long totalCount = notificationDAO.getNotificationCountForUser(testUser.getUsername());
        assertEquals(3, totalCount);
    }

    @Test
    void testMarkAsRead() throws ApiException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.INFO);
        dto.setNotificationTitle("Mark read test");
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());

        NotificationDTO created = notificationDAO.createNotification(dto, testUser);

        NotificationDTO marked = notificationDAO.markAsRead(created.getId(), testUser.getUsername());
        assertTrue(marked.isRead());
    }

    @Test
    void testMarkAllAsRead() {
        NotificationDTO dto1 = new NotificationDTO();
        dto1.setNotificationType(NotificationType.INFO);
        dto1.setNotificationTitle("Unread 1");
        dto1.setRead(false);
        dto1.setCreatedAt(LocalDateTime.now());

        NotificationDTO dto2 = new NotificationDTO();
        dto2.setNotificationType(NotificationType.INFO);
        dto2.setNotificationTitle("Unread 2");
        dto2.setRead(false);
        dto2.setCreatedAt(LocalDateTime.now());

        notificationDAO.createNotification(dto1, testUser);
        notificationDAO.createNotification(dto2, testUser);

        int markedCount = notificationDAO.markAllAsRead(testUser.getUsername());
        assertEquals(2, markedCount);

        long unreadCount = notificationDAO.countUnreadForUser(testUser.getUsername());
        assertEquals(0, unreadCount);
    }

    @Test
    void testGetAllForUser() {
        NotificationDTO dto1 = new NotificationDTO();
        dto1.setNotificationType(NotificationType.INFO);
        dto1.setNotificationTitle("Note 1");
        dto1.setRead(false);
        dto1.setCreatedAt(LocalDateTime.now());

        NotificationDTO dto2 = new NotificationDTO();
        dto2.setNotificationType(NotificationType.INFO);
        dto2.setNotificationTitle("Note 2");
        dto2.setRead(false);
        dto2.setCreatedAt(LocalDateTime.now());

        notificationDAO.createNotification(dto1, testUser);
        notificationDAO.createNotification(dto2, testUser);

        List<NotificationDTO> list = notificationDAO.getAllForUser(testUser.getUsername());
        assertEquals(2, list.size());
        assertEquals("Note 2", list.get(0).getNotificationTitle());
    }
}
