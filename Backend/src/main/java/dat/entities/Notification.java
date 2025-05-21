package dat.entities;

import dat.dtos.NotificationDTO;
import dat.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // sætter er lige nu for alle attributter, men skal nok ændres til kun de relevante
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String NotificationTitle;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String link;

    private Long teamId;

    private Long invitationId;

    private Long senderId;


    public Notification(User user, NotificationType notificationType, String NotificationTitle, boolean isRead, LocalDateTime createdAt, String link, Long teamId, Long invitationId, Long senderId)
    {
        this.user = user;
        this.notificationType = notificationType;
        this.NotificationTitle = NotificationTitle;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.link = link;
        this.teamId = teamId;
        this.invitationId = invitationId;
        this.senderId = senderId;
    }

    public Notification (NotificationDTO notificationDTO, User user) {

        this.id = notificationDTO.getId();
        this.user = user;
        this.notificationType = notificationDTO.getNotificationType();
        this.NotificationTitle = notificationDTO.getNotificationTitle();
        this.isRead = notificationDTO.isRead();
        this.createdAt = notificationDTO.getCreatedAt();
        this.link = notificationDTO.getLink();
        this.teamId = notificationDTO.getTeamId();
        this.invitationId = notificationDTO.getInvitationId();
        this.senderId = notificationDTO.getSenderId();

    }


    // Sæt createdAt automatisk ved første gemning
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
