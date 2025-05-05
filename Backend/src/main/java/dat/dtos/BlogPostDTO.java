package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat.entities.BlogPost;
import dat.enums.BlogPostStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostDTO {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime updatedAt;

    // Format the enum as a string, might not be necessary/needed
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BlogPostStatus status;

    public BlogPostDTO(BlogPost blogPost) {
        this.id = blogPost.getId();
        this.userId = (long) blogPost.getUser().getId();
        this.title = blogPost.getTitle();
        this.content = blogPost.getContent();
        this.createdAt = blogPost.getCreatedAt();
        this.updatedAt = blogPost.getUpdatedAt();
        this.status = blogPost.getStatus();
    }


}