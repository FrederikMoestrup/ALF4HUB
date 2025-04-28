package US3.steps;

import dat.config.HibernateConfig;
import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import dat.enums.BlogPostStatus;
import io.cucumber.java.Before;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class BlogPostSteps {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final BlogPostDAO blogPostDAO;
    private BlogPostDTO blogPostDTO;
    private List<BlogPostDTO> blogPosts;
    private List<BlogPostDTO> blogPostWithOnlyContentPreview;


    public BlogPostSteps() {
        blogPostDAO = BlogPostDAO.getInstance(emf);
    }

    @Before
    public void cleanUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM BlogPost").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Given("a user wants to create a blog post")
    public void aUserWantsToCreateABlogPost() {
        blogPostDTO = new BlogPostDTO();
    }

    @When("they provide the title {string} and content {string}")
    public void theyProvideTheTitleAndContent(String title, String content) {
        blogPostDTO.setUserId(1L);
        blogPostDTO.setTitle(title);
        blogPostDTO.setContent(content);
        blogPostDTO.setStatus(BlogPostStatus.READY);
    }

    @And("the blog post is saved")
    public void theBlogPostIsSaved() {
        blogPostDTO = blogPostDAO.create(blogPostDTO);
    }

    @Then("the post should be created successfully")
    public void thePostShouldBeCreatedSuccessfully() {
        assertThat(blogPostDTO.getId(), is(notNullValue()));
        assertThat(blogPostDTO.getTitle(), is("My First Blog Post"));
        assertThat(blogPostDTO.getContent(), is("This is the content of my first blog post."));
    }

    @Given("a blog post exists with title {string} and content {string}")
    public void aBlogPostExistsWithTitleAndContent(String title, String content) {
        blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(1L);
        blogPostDTO.setTitle(title);
        blogPostDTO.setContent(content);
        blogPostDTO.setStatus(BlogPostStatus.READY);
        blogPostDTO = blogPostDAO.create(blogPostDTO);
    }

    @When("I retrieve the blog post by ID")
    public void iRetrieveTheBlogPostByID() {
        blogPostDTO = blogPostDAO.getById(blogPostDTO.getId());
        assertThat(blogPostDTO, is(notNullValue()));
    }

    @Then("the title should be {string}")
    public void theTitleShouldBe(String title) {
        assertThat(blogPostDTO.getTitle(), is(title));
    }

    @And("the content should be {string}")
    public void theContentShouldBe(String content) {
        assertThat(blogPostDTO.getContent(), is(content));
    }

    @Given("multiple blog posts exist")
    public void multipleBlogPostsExist() {
        blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(2L);
        blogPostDTO.setTitle("My First Blog Post By User 2");
        blogPostDTO.setContent("This is the content of my first blog post as User 2.");
        blogPostDTO.setStatus(BlogPostStatus.READY);
        blogPostDAO.create(blogPostDTO);

        blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(2L);
        blogPostDTO.setTitle("My Second Blog Post By User 2");
        blogPostDTO.setContent("This is the content of my second blog post as User 2.");
        blogPostDTO.setStatus(BlogPostStatus.READY);
        blogPostDAO.create(blogPostDTO);

        blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(3L);
        blogPostDTO.setTitle("My First Blog Post By User 3");
        blogPostDTO.setContent(
                "This is the content of my first blog post as User 3. " +
                        "In today’s fast-paced digital world, " +
                        "having a space to share your thoughts, ideas, " +
                        "and stories is more important than ever. " +
                        "Blogging allows individuals to express themselves, " +
                        "connect with others, and build an audience around topics they care about. " +
                        "Whether you're sharing personal experiences, professional insights, " +
                        "or creative writing, a well-crafted blog post can inform, inspire, " +
                        "and entertain readers around the world");
        blogPostDTO.setStatus(BlogPostStatus.READY);
        blogPostDAO.create(blogPostDTO);
    }

    @When("I retrieve all blog posts")
    public void iRetrieveAllBlogPosts() {
        blogPosts = blogPostDAO.getAll();
    }

    @Then("I should see a list of all blog posts")
    public void iShouldSeeAListOfAllBlogPosts() {
        assertThat(blogPosts, is(notNullValue()));
        assertThat(blogPosts.size(), is(3));
        assertThat(blogPosts.get(0).getContent(), is("This is the content of my first blog post as User 2."));
        assertThat(blogPosts.get(1).getContent(), is("This is the content of my second blog post as User 2."));
        assertThat(blogPosts.get(2).getTitle(), is("My First Blog Post By User 3"));
    }

    @When("I retrieve all blog posts with only preview of content")
    public void iRetrieveAllBlogPostsWithOnlyPreviewOfContent() {
        blogPostWithOnlyContentPreview = blogPostDAO.getAllWithOnlyContentPreview();
    }

    @Then("I should see a list of all blog posts with only a preview of their content")
    public void iShouldSeeAListOfAllBlogPostsWithOnlyAPreviewOfTheirContent() {
        assertThat(blogPostWithOnlyContentPreview, is(notNullValue()));
        assertThat(blogPostWithOnlyContentPreview.size(), is(3));
        assertThat(blogPostWithOnlyContentPreview.get(0).getContent(), is("This is the content of my first blog post as User 2."));
        assertThat(blogPostWithOnlyContentPreview.get(1).getContent(), is("This is the content of my second blog post as User 2."));
        assertThat(blogPostWithOnlyContentPreview.get(2).getContent(), is("This is the content of my first blog post as User 3. " +
                "In today’s fast-paced digital world, having a space to share your thoughts, ideas, and stories is"));
    }
}
