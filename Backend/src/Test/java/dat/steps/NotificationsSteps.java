package dat.steps;

import dat.config.HibernateConfig;
import dat.daos.NotificationDAO;
import dat.dtos.NotificationDTO;
import dat.entities.User;
import dat.enums.NotificationType;
import dat.exceptions.ApiException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class NotificationsSteps {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final NotificationDAO notificationDAO;
    private User testUser;
    private NotificationDTO currentNotification;
    private NotificationDTO fetchedNotification;
    private List<NotificationDTO> notifications = new ArrayList<>();
    private Exception lastException;

    public NotificationsSteps() {
        notificationDAO = NotificationDAO.getInstance(emf);
    }

    @Before
    public void setUp() {
        testUser = new User();
        testUser.setUsername("lars");
        testUser.setPassword("test123");
        testUser.setEmail("lars@example.com");

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testUser);
            em.getTransaction().commit();
            System.out.println("User persisted: " + testUser.getUsername() + ", email: " + testUser.getEmail());
        }
    }

    @After
    public void cleanUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Notification").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        }
        notifications = new ArrayList<>();
        currentNotification = null;
        fetchedNotification = null;
        lastException = null;
    }

    @Given("a user {string} with password {string} and email {string} exists")
    public void a_user_exists(String username, String password, String email) {
        assertThat(testUser.getUsername(), is(username));
        assertThat(testUser.getPassword(), is(password));
        assertThat(testUser.getEmail(), is(email));
    }

    @Given("all notifications are deleted")
    public void all_notifications_are_deleted() {
        // Handled in cleanUp()
    }

    @Given("a notification with type {word} and title {string} and unread status")
    public void a_notification_with_type_and_title_and_unread_status(String type, String title) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(NotificationType.valueOf(type));
        dto.setNotificationTitle(title);
        dto.setRead(false);
        dto.setCreatedAt(LocalDateTime.now());
        currentNotification = dto;
    }

    @Given("notifications:")
    public void notifications(io.cucumber.datatable.DataTable table) {
        // Skift fra clear() til ny ArrayList for at undgå immutable collection fejl
        notifications = new ArrayList<>();
        for (Map<String, String> row : table.asMaps(String.class, String.class)) {
            NotificationDTO dto = new NotificationDTO();
            dto.setNotificationType(NotificationType.valueOf(row.get("type")));
            dto.setNotificationTitle(row.get("title"));
            dto.setRead(Boolean.parseBoolean(row.get("read")));
            dto.setCreatedAt(LocalDateTime.now());
            notifications.add(dto);
        }
    }

    @When("the notification is created for user {string}")
    public void the_notification_is_created_for_user(String username) {
        currentNotification = notificationDAO.createNotification(currentNotification, testUser);
    }

    @When("notifications are created for user {string}")
    public void notifications_are_created_for_user(String username) {
        List<NotificationDTO> createdList = new ArrayList<>();
        for (NotificationDTO dto : notifications) {
            NotificationDTO created = notificationDAO.createNotification(dto, testUser);
            createdList.add(created);
        }
        notifications = createdList;
    }

    @When("I get the notification by id")
    public void i_get_the_notification_by_id() {
        try {
            fetchedNotification = notificationDAO.getById(currentNotification.getId());
        } catch (ApiException e) {
            lastException = e;
            fetchedNotification = null;
        }
    }

    @When("the notification is updated to title {string} and read status")
    public void the_notification_is_updated_to_title_and_read_status(String updatedTitle) {
        try {
            currentNotification.setNotificationTitle(updatedTitle);
            currentNotification.setRead(true);
            currentNotification = notificationDAO.update(currentNotification.getId(), currentNotification);
        } catch (ApiException e) {
            lastException = e;
        }
    }

    @When("the notification is deleted")
    public void the_notification_is_deleted() {
        try {
            currentNotification = notificationDAO.delete(currentNotification.getId());
        } catch (ApiException e) {
            lastException = e;
        }
    }

    @When("the notification is marked as read")
    public void the_notification_is_marked_as_read() {
        try {
            currentNotification = notificationDAO.markAsRead(currentNotification.getId(), testUser.getUsername());
        } catch (ApiException e) {
            lastException = e;
        }
    }

    @When("all notifications are marked as read for user {string}")
    public void all_notifications_are_marked_as_read_for_user(String username) {
        notificationDAO.markAllAsRead(username);
    }

    @When("I get all notifications for user {string}")
    public void i_get_all_notifications_for_user(String username) {
        notifications = notificationDAO.getAllForUser(username);
    }

    @Then("the notification id should be positive")
    public void the_notification_id_should_be_positive() {
        assertThat(currentNotification, is(notNullValue()));
        assertThat(currentNotification.getId(), is(notNullValue()));
        assertThat(currentNotification.getId() > 0, is(true));
    }

    @Then("the notification title should be {string}")
    public void the_notification_title_should_be(String expectedTitle) {
        assertThat(currentNotification.getNotificationTitle(), is(expectedTitle));
    }

    @Then("the notification should be unread")
    public void the_notification_should_be_unread() {
        assertThat(currentNotification.isRead(), is(false));
    }

    @Then("the notification should be read")
    public void the_notification_should_be_read() {
        assertThat(currentNotification.isRead(), is(true));
    }

    @Then("getting the notification by id should fail")
    public void getting_the_notification_by_id_should_fail() {
        assertThat(fetchedNotification, is(nullValue()));
    }

    @Then("the unread count for user {string} should be {int}")
    public void the_unread_count_for_user_should_be(String username, int expectedCount) {
        long count = notificationDAO.countUnreadForUser(username);
        assertThat((int) count, is(expectedCount));
    }

    @Then("the total notification count for user {string} should be {int}")
    public void the_total_notification_count_for_user_should_be(String username, int expectedCount) {
        long count = notificationDAO.getNotificationCountForUser(username);
        assertThat((int) count, is(expectedCount));
    }

    @Then("the notification list size should be {int}")
    public void the_notification_list_size_should_be(int expectedSize) {
        assertThat(notifications.size(), is(expectedSize));
    }

    @Then("the first notification title should be {string}")
    public void the_first_notification_title_should_be(String expectedTitle) {
        assertThat(notifications.get(0).getNotificationTitle(), is(expectedTitle));
    }
}
