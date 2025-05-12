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
                query = "SELECT b FROM BlogPost b WHERE b.status = 'PUBLISHED'"
        ),
        @NamedQuery(
                name = "BlogPost.findAllWithOnlyContentPreview",
                query = "SELECT new dat.dtos.BlogPostDTO(bp.id,CAST(bp.user.id AS Long), bp.title, SUBSTRING(bp.content, 1, 150), bp.createdAt, bp.updatedAt, bp.status) FROM BlogPost bp WHERE bp.status = 'PUBLISHED'"
        ),
        @NamedQuery(
                name = "BlogPost.getDraftsByUserId",
                query = "SELECT bp FROM BlogPost bp WHERE bp.user.id = :userId AND bp.status = 'DRAFT'"
        )
})
@Table(name = "blog_post")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private BlogPostStatus status;

    public BlogPost(BlogPostDTO blogPostDTO, User user) {
        this.id = blogPostDTO.getId();
        this.user = user;
        this.title = blogPostDTO.getTitle();
        this.content = blogPostDTO.getContent();
        this.createdAt = blogPostDTO.getCreatedAt();
        this.updatedAt = blogPostDTO.getUpdatedAt();
        this.status = blogPostDTO.getStatus();
    }
}