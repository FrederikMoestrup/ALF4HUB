package dat.services;

/**
  Service-klasse der håndterer oprettelsen af notifikationer relateret til teams.
  Bruges til at sende en notifikation til en spiller,
  når deres ansøgning til et hold er blevet accepteret.

  Bruges til at spilleren kan afvise en notifikation

  opgave #156 og #157 fra taiga
 **/



import dat.dtos.NotificationDTO;
import dat.entities.User;
import dat.daos.NotificationDAO;

public class TeamsNotificationService {

    private final NotificationDAO notificationDAO;

    public TeamsNotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public void createAcceptedApplicationNotification(User playerUser, long teamId, User captain) {
        NotificationDTO notification = new NotificationDTO();
        notification.setNotificationTitle("Din ansøgning til holdet er blevet accepteret");
        notification.setNotificationType("APPLICATION_ACCEPTED");
        notification.setRead(false);
        notification.setLink("/teams/" + teamId);
        notification.setTeamId(teamId);
        notification.setSenderId((long) captain.getId());

        notificationDAO.create(notification, playerUser);
    }

        public void createRejectedApplicationNotification(User playerUser, long teamId, User captain) {
            NotificationDTO notification = new NotificationDTO();
            notification.setNotificationTitle("Din ansøgning til holdet er blevet afvist");
            notification.setNotificationType("APPLICATION_REJECTED");
            notification.setRead(false);
            notification.setLink("/teams/" + teamId);
            notification.setTeamId(teamId);
            notification.setSenderId((long) captain.getId());

            notificationDAO.create(notification, playerUser);
        }

}
