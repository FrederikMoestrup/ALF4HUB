package US7.dat.daos;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.entities.BlogPost;
import dat.enums.BlogPostStatus;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

        // Create DTO test data
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(10L);
        blogPostDTO.setTitle("Test Title");
        blogPostDTO.setContent("Test Content");
        blogPostDTO.setStatus(BlogPostStatus.READY);

        BlogPostDTO blogPostDTO2 = new BlogPostDTO();
        blogPostDTO2.setUserId(20L);
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
        List<BlogPost> blogPostsEntities = new ArrayList<>();
        BlogPost bp1 = new BlogPost(blogPostDTO);
        BlogPost bp2 = new BlogPost(blogPostDTO2);
        blogPostsEntities.add(bp1);
        blogPostsEntities.add(bp2);

        // Persist the test data
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            blogPostsEntities.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BlogPost").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE blog_post_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }


    @Test
    void update() throws ApiException {

        List<BlogPostDTO> allPosts = blogPostDAO.getAll();
        BlogPostDTO existingPost = allPosts.get(0);

        BlogPostDTO updateDTO = new BlogPostDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setContent("Updated Content");


        BlogPostDTO updatedPost = blogPostDAO.update(existingPost.getId(), updateDTO);


        assertThat(updatedPost.getTitle(), equalTo("Updated Title"));
        assertThat(updatedPost.getContent(), equalTo("Updated Content"));


        BlogPostDTO nonExistingUpdateDTO = new BlogPostDTO();
        nonExistingUpdateDTO.setTitle("Will fail");
        nonExistingUpdateDTO.setContent("Will also fail");


        ApiException exception = assertThrowsExactly(ApiException.class, () -> {
            blogPostDAO.update(9999L, nonExistingUpdateDTO);
        });
        assertThat(exception.getStatusCode(), equalTo(404));
    }




    @Test
    void delete() throws ApiException {
        List<BlogPostDTO> allPosts = blogPostDAO.getAll();

        // Printing all posts before any deletion
        allPosts.forEach(blogPostDTO -> System.out.println(blogPostDTO.getId() + ", " + blogPostDTO.getTitle() + ", " + blogPostDTO.getContent()));

        BlogPostDTO existingPost = allPosts.get(0);
        BlogPostDTO deletedPost = blogPostDAO.delete(existingPost.getId());
        List<BlogPostDTO> allPostsAfterDelete = blogPostDAO.getAll();

        assertThat(deletedPost.getId(), equalTo(existingPost.getId()));
        assertThat(deletedPost.getTitle(), equalTo(existingPost.getTitle()));
        assertThat(deletedPost.getContent(), equalTo(existingPost.getContent()));
        assertThat(deletedPost.getStatus(), equalTo(existingPost.getStatus()));
        assertThat(deletedPost.getUserId(), equalTo(existingPost.getUserId()));

        // Printing all posts after one of them has been deleted
        allPostsAfterDelete.forEach(blogPostDTO -> System.out.println(blogPostDTO.getId() + ", " + blogPostDTO.getTitle() + ", " + blogPostDTO.getContent()));

        assertFalse(blogPostDAO.getAll().contains(existingPost));


        ApiException exception = assertThrowsExactly(ApiException.class, () -> {
            blogPostDAO.delete(9999L);
        });
        assertThat(exception.getStatusCode(), equalTo(404));



    }
}

