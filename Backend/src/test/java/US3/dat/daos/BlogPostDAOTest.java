package US3.dat.daos;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.dtos.UserDTO;
import dat.entities.BlogPost;
import dat.entities.User;
import dat.enums.BlogPostStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class BlogPostDAOTest {

    private static EntityManagerFactory emf;
    private BlogPostDAO blogPostDAO;

    @BeforeAll
    static void setupClass() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @AfterAll
    static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        blogPostDAO = BlogPostDAO.getInstance(emf);

        //Create user dto test data
        UserDTO userDTO1 = new UserDTO("username1", "test1", "test@test");
        UserDTO userDTO2 = new UserDTO("username2", "test2","test@test");
        UserDTO userDTO3 = new UserDTO("username3", "test3","test@test");

        // Create blog post DTO test data
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(1L);
        blogPostDTO.setTitle("Test Title");
        blogPostDTO.setContent("Test Content");
        blogPostDTO.setStatus(BlogPostStatus.READY);

        BlogPostDTO blogPostDTO2 = new BlogPostDTO();
        blogPostDTO2.setUserId(2L);
        blogPostDTO2.setTitle("Test Title 2");
        blogPostDTO2.setContent("Test Content 2. " +
                "This is a longer content to test the preview. " +
                "In today’s fast-paced digital world, " +
                "having a space to share your thoughts, ideas, " +
                "and stories is more important than ever. " +
                "Blogging allows individuals to express themselves, " +
                "connect with others, and build an audience around topics they care about. " +
                "Whether you're sharing personal experiences, professional insights, " +
                "or creative writing, a well-crafted blog post can inform, inspire, " +
                "and entertain readers around the world");
        blogPostDTO2.setStatus(BlogPostStatus.READY);

        // Transform to entity test data
        List<User> userEntities = new ArrayList<>();
        User u1 = new User(userDTO1.getUsername(), userDTO1.getPassword());
        User u2 = new User(userDTO2.getUsername(), userDTO2.getPassword());
        User u3 = new User(userDTO3.getUsername(), userDTO3.getPassword());
        userEntities.add(u1);
        userEntities.add(u2);
        userEntities.add(u3);

        List<BlogPost> blogPostsEntities = new ArrayList<>();
        BlogPost bp1 = new BlogPost(blogPostDTO, u1);
        BlogPost bp2 = new BlogPost(blogPostDTO2, u2);
        blogPostsEntities.add(bp1);
        blogPostsEntities.add(bp2);

        // Persist the test data
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            userEntities.forEach(em::persist);
            blogPostsEntities.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BlogPost").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE blog_post_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE users_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void getById() {
        BlogPostDTO foundBlogPost = blogPostDAO.getById(1L);

        assertThat(foundBlogPost, is(notNullValue()));
        assertThat(foundBlogPost.getUserId(), is(1L));
        assertThat(foundBlogPost.getTitle(), is("Test Title"));
        assertThat(foundBlogPost.getContent(), is("Test Content"));
    }

    @Test
    void getByIdNotFound() {
        assertThrowsExactly(
                EntityNotFoundException.class,
                () -> blogPostDAO.getById(100L),
                "Blog Post with id 100 could not be found"
        );
    }

    @Test
    void getAll() {
        List<BlogPostDTO> foundBlogPosts = blogPostDAO.getAll();
        assertThat(foundBlogPosts, is(notNullValue()));
        assertThat(foundBlogPosts.size(), is(2));
        assertThat(foundBlogPosts.get(0).getTitle(), is("Test Title"));
        assertThat(foundBlogPosts.get(1).getTitle(), is("Test Title 2"));
    }

    @Test
    void getAllWithOnlyContentPreview() {
        List<BlogPostDTO> foundBlogPosts = blogPostDAO.getAllWithOnlyContentPreview();
        assertThat(foundBlogPosts, is(notNullValue()));
        assertThat(foundBlogPosts.size(), is(2));
        assertThat(foundBlogPosts.get(1).getContent(), is("Test Content 2. " +
                "This is a longer content to test the preview. " +
                "In today’s fast-paced digital world, having a space to share your thoughts, ideas, and s"));
    }

    @Test
    void create() {
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(3L);
        blogPostDTO.setTitle("Another Test Title");
        blogPostDTO.setContent("Another Test Content");
        blogPostDTO.setStatus(BlogPostStatus.READY);

        BlogPostDTO createdBlogPost = blogPostDAO.create(blogPostDTO);

        assertThat(createdBlogPost.getId(), is(notNullValue()));
        assertThat(createdBlogPost.getUserId(), is(3L));
        assertThat(createdBlogPost.getTitle(), is("Another Test Title"));
        assertThat(createdBlogPost.getContent(), is("Another Test Content"));
    }

    @Test
    void createWithDraftStatus() {
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(3L);
        blogPostDTO.setTitle("Another Test Title again");
        blogPostDTO.setContent("Another Test Content again");
        blogPostDTO.setStatus(BlogPostStatus.DRAFT);

        assertThrowsExactly(
                IllegalStateException.class,
                () -> blogPostDAO.create(blogPostDTO),
                "Blog post is not ready to be saved - it needs to be reviewed."
        );
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }


}