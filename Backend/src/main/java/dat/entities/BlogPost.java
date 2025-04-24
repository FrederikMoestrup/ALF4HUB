package dat.entities;

import dat.dtos.BlogPostDTO;
import dat.enums.BlogPostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "BlogPost.getAll",
                query = "SELECT bp FROM BlogPost bp"
        ),
        @NamedQuery(
                name = "BlogPost.findAllWithOnlyContentPreview",
                query = "SELECT new dat.dtos.BlogPostDTO(bp.id, bp.userId, bp.title, SUBSTRING(bp.content, 1, 150), bp.createdAt, bp.updatedAt, bp.status) FROM BlogPost bp"
        )
})
@Table(name = "blog_post")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    // A user can have multiple blog posts, but a blog post belongs to one user
    // Who should be responsible for the relation and where should it be managed?
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000) // Possibly a different length - maybe use @Lob?
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Should there be a default, perhaps PENDING_REVIEW or DRAFT?
    @Enumerated(EnumType.STRING)
    private BlogPostStatus status;

    public BlogPost(BlogPostDTO blogPostDTO) {
        this.id = blogPostDTO.getId();
        this.userId = blogPostDTO.getUserId();
        this.title = blogPostDTO.getTitle();
        this.content = blogPostDTO.getContent();
        this.createdAt = blogPostDTO.getCreatedAt();
        this.updatedAt = blogPostDTO.getUpdatedAt();
        this.status = blogPostDTO.getStatus();
    }

}