package US20;

import dat.daos.BlogPostDAO;
import dat.dtos.BlogPostDTO;
import io.cucumber.java.en.*;

import static org.junit.Assert.assertNotNull;

public class BlogAC1Steps {

    private BlogPostDAO blogPostDAO;
    private BlogPostDTO blogPostDTO;

    @Given("^I see a blog post in the list$")
    public void iSeeABlogPostInTheList() {
        blogPostDAO = new BlogPostDAO();
    }

    @When("^i click on the blog post$")
    public void iClickOnTheBlogPost() {
        blogPostDTO = blogPostDAO.getById(1L);
    }

    @Then("^I should be taken to a page with the full content, author and date$")
    public void iShouldBeTakenToAPageWithTheFullContentAuthorAndDate() {
        assertNotNull(blogPostDTO);
        assertNotNull(blogPostDTO.getTitle());
        assertNotNull(blogPostDTO.getContent());
        assertNotNull(blogPostDTO.getUserId());
        assertNotNull(blogPostDTO.getUpdatedAt());
    }
}
