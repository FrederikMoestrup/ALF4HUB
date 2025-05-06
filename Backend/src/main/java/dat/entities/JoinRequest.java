package dat.entities;

import dat.enums.JoinRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_request_id", nullable = false, unique = true)
    protected int id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected JoinRequestStatus status = JoinRequestStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    protected User requester;

    public JoinRequest(User requester) {
        this.requester = requester;
        this.status = JoinRequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}
