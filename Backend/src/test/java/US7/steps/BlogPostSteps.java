package US7.steps;

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


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BlogPostSteps {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final BlogPostDAO blogPostDAO;
    private BlogPostDTO blogPostDTO;
    private String confirmationMessage;

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


    @Given("the user is on their blog section and selects an existing blog post")
    public void theUserSelectsAnExistingBlogPost() {
        blogPostDTO = new BlogPostDTO();
        blogPostDTO.setUserId(1L);
        blogPostDTO.setTitle("New Title");
        blogPostDTO.setContent("New content of the blog post.");
        blogPostDTO.setStatus(BlogPostStatus.READY);
        blogPostDTO = blogPostDAO.create(blogPostDTO);
    }

    @And("clicks the edit button")
    public void clicksTheEditButton() {

    }

    @When("the user edits the title and content of the blog post and clicks Save")
    public void userEditsTitleAndContentAndClicksSave() {
        blogPostDTO.setTitle("Updated Title");
        blogPostDTO.setContent("Updated content of the blog post.");
        try {
            blogPostDTO = blogPostDAO.update(blogPostDTO.getId(), blogPostDTO);
            confirmationMessage = "Blogpostet er opdateret succesfuldt.";
        } catch (Exception e) {
            e.printStackTrace();
            confirmationMessage = "Noget gik galt under opdateringen. Prøv igen";
        }
    }

    @Then("the changes are successfully saved")
    public void theChangesAreSuccessfullySaved() {
        BlogPostDTO updatedPost = blogPostDAO.getById(blogPostDTO.getId());
        assertThat(updatedPost.getTitle(), is("Updated Title"));
        assertThat(updatedPost.getContent(), is("Updated content of the blog post."));
    }

    @And("the user receives a confirmation message: {string}")
    public void theUserReceivesAConfirmationMessage(String expectedMessage) {
        assertEquals(expectedMessage, confirmationMessage);
    }

    @And("the timestamp of the last edit is updated and shown to the user")
    public void theTimestampOfLastEditIsUpdated() {
        BlogPostDTO updatedPost = blogPostDAO.getById(blogPostDTO.getId());
        assertThat(updatedPost.getUpdatedAt(), is(notNullValue()));
        System.out.println("Last edited at: " + updatedPost.getUpdatedAt());
    }
}

