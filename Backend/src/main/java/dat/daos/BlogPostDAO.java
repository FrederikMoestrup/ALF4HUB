package dat.daos;

import dat.dtos.BlogPostDTO;
import dat.entities.BlogPost;
import dat.enums.BlogPostStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostDAO implements IDAO<BlogPostDTO, Long> {
    private static BlogPostDAO instance;
    private static EntityManagerFactory emf;

    public static BlogPostDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BlogPostDAO();
        }
        return instance;
    }

    @Override
    public BlogPostDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            BlogPost foundBlogPost = em.find(BlogPost.class, id);

            if (foundBlogPost == null) {
                throw new EntityNotFoundException(String.format("Blog Post with id %s could not be found", id));
            }

            return new BlogPostDTO(foundBlogPost);
        }
    }

    @Override
    public List<BlogPostDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BlogPost> query = em.createNamedQuery("BlogPost.getAll", BlogPost.class);
            List<BlogPost> blogPosts = query.getResultList();

            return blogPosts.stream()
                    .map(BlogPostDTO::new)
                    .toList();
        }
    }

    public List<BlogPostDTO> getAllWithOnlyContentPreview() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BlogPostDTO> blogPostDTOs = em.createNamedQuery("BlogPost.findAllWithOnlyContentPreview", BlogPostDTO.class);

            return blogPostDTOs.getResultStream()
                    .toList();
        }
    }

    @Override
    public BlogPostDTO create(BlogPostDTO blogPostDTO) {
        try (EntityManager em = emf.createEntityManager()) {

            //if (blogPostDTO.getStatus() != BlogPostStatus.READY) {
            //  throw new IllegalStateException("Blog post is not ready to be saved - it needs to be reviewed.");
            // }

            // 1. Remove the READY status validation (this was blocking drafts)
            if (blogPostDTO.getTitle() == null || blogPostDTO.getContent() == null) {
                throw new IllegalArgumentException("Title and content are required");
            }

            BlogPost newBlogPost = new BlogPost(blogPostDTO);
            // TODO: Set the new status to PUBLISHED or DRAFT depending on the context
            // Currently we're only able to publish in our given US
            // We might have to take in status in the param in the future

            // If status wasn't provided in DTO, use default from entity (DRAFT)
            // If provided, respect the requested status (but validate transitions if needed)
            if (blogPostDTO.getStatus() != null) {
                newBlogPost.setStatus(blogPostDTO.getStatus());
            }

            em.getTransaction().begin();
            em.persist(newBlogPost);
            em.getTransaction().commit();

            return new BlogPostDTO(newBlogPost);
        }
    }

    // New dedicated method for draft creation
    public BlogPostDTO createDraft(BlogPostDTO blogPostDTO) {
        // Force DRAFT status regardless of what DTO contains
        blogPostDTO.setStatus(BlogPostStatus.DRAFT);
        return create(blogPostDTO);
    }

    // Optional method for direct publishing
    public BlogPostDTO publishBlogPost(BlogPostDTO blogPostDTO) {
        // Additional validation could be added here
        blogPostDTO.setStatus(BlogPostStatus.PUBLISHED);
        return create(blogPostDTO);
    }

    // new method to get drafts by user ID
    public List<BlogPostDTO> getDraftsByUser(Long userId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BlogPost> query = em.createNamedQuery("BlogPost.findDraftsByUser", BlogPost.class);
            query.setParameter("userId", userId);
            List<BlogPost> drafts = query.getResultList();

            return drafts.stream()
                    .map(BlogPostDTO::new)
                    .toList();
        }
    }

    // new method to update status (draft -> published)
    public BlogPostDTO updateStatus(Long id, BlogPostStatus newStatus) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            BlogPost blogPost = em.find(BlogPost.class, id);
            if (blogPost == null) {
                throw new EntityNotFoundException("Blog post not found");
            }

            // Validate post exists
            if (blogPost.getStatus() == BlogPostStatus.PUBLISHED && newStatus == BlogPostStatus.DRAFT) {
                throw new IllegalStateException("Cannot revert published post to draft");
            }

            blogPost.setStatus(newStatus); // Update status - @UpdateTimestamp will handle updatedAt automatically
            em.getTransaction().commit();

            return new BlogPostDTO(blogPost);
        }
    }



    @Override
    public BlogPostDTO update(Long id, BlogPostDTO blogPostDTO) {
        return null;
    }

    @Override
    public BlogPostDTO delete(Long id) {
        return null;
    }
}