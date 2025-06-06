package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat.entities.Notification;
import dat.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    private int id;
    private int userId;
    private NotificationType notificationType;
    private String notificationTitle;
    private boolean isRead;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
    private String link;
    private Long teamId;
    private Long invitationId;
    private Long senderId;

    public NotificationDTO(int userId, NotificationType notificationType, String notificationTitle, boolean isRead, LocalDateTime createdAt, String link, Long teamId, Long invitationId, Long senderId) {
        this.userId = userId;
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.link = link;
        this.teamId = teamId;
        this.invitationId = invitationId;
        this.senderId = senderId;
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId(); // relation -> userId
        this.notificationType = notification.getNotificationType();
        this.notificationTitle = notification.getNotificationTitle();
        this.isRead = notification.isRead();
        this.createdAt = notification.getCreatedAt();
        this.link = notification.getLink();
        this.teamId = notification.getTeamId();
        this.invitationId = notification.getInvitationId();
        this.senderId = notification.getSenderId();
    }
}
