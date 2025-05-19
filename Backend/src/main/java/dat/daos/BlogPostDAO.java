package dat.daos;

import dat.dtos.BlogPostDTO;
import dat.entities.BlogPost;
import dat.entities.User;
import dat.enums.BlogPostStatus;
import dat.exceptions.ApiException;
import jakarta.persistence.*;
import org.hibernate.ObjectNotFoundException;

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

    public List<BlogPostDTO> getDraftByUserId(int userId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BlogPost> query = em.createNamedQuery("BlogPost.getDraftsByUserId", BlogPost.class);
            query.setParameter("userId", userId);
            List<BlogPost> blogPosts = query.getResultList();

            return blogPosts.stream().map(BlogPostDTO::new).toList();
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

            // TODO: Perhaps replace with the getById method from User DAO later (if there is one)?
            int userId = blogPostDTO.getUserId().intValue();
            User user = em.find(User.class,  userId);
            if (user == null) {
                throw new EntityNotFoundException("User with id " + userId + " not found.");
            }

            if (blogPostDTO.getStatus() != BlogPostStatus.READY) {
                throw new IllegalStateException("Blog post is not ready to be saved - it needs to be reviewed.");
            }

            BlogPost newBlogPost = new BlogPost(blogPostDTO, user);
            newBlogPost.setStatus(BlogPostStatus.PUBLISHED);

            em.getTransaction().begin();
            em.persist(newBlogPost);
            em.getTransaction().commit();

            return new BlogPostDTO(newBlogPost);
        }
    }

    public BlogPostDTO saveAsDraft(BlogPostDTO blogPostDTO) {
        try (EntityManager em = emf.createEntityManager()) {

            // TODO: Perhaps replace with the getById method from User DAO later (if there is one)?
            int userId = blogPostDTO.getUserId().intValue();
            User user = em.find(User.class,  userId);
            if (user == null) {
                throw new EntityNotFoundException("User with id " + userId + " not found.");
            }

            BlogPost newDraft = new BlogPost(blogPostDTO, user);
            newDraft.setStatus(BlogPostStatus.DRAFT);

            em.getTransaction().begin();
            em.persist(newDraft);
            em.getTransaction().commit();

            return new BlogPostDTO(newDraft);
        }
    }


    @Override
    public BlogPostDTO update(Long id, BlogPostDTO blogPostDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            BlogPost blogPost = em.find(BlogPost.class, id);
            if (blogPost == null) {
                throw new ApiException(404, "Blogpost med ID " + id + " blev ikke fundet.");
            }

            blogPost.setTitle(blogPostDTO.getTitle());
            blogPost.setContent(blogPostDTO.getContent());

            em.getTransaction().commit();

            return new BlogPostDTO(blogPost);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500, "Noget gik galt under opdateringen. Prøv igen senere");
        }
    }


    @Override
    public BlogPostDTO delete(Long id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            BlogPost blogPost = em.find(BlogPost.class, id);
            if (blogPost == null) {
                throw new ApiException(404, "Blogpost med ID " + id + " blev ikke fundet.");

            }

            em.remove(blogPost);
            em.getTransaction().commit();

            return new BlogPostDTO(blogPost);
        }
        catch (ApiException e) {
            throw e;
        }
        catch (Exception e) {

            e.printStackTrace();
            throw new ApiException(500, "Noget gik galt under sletningen. Prøv igen.");
        }
    }
}




