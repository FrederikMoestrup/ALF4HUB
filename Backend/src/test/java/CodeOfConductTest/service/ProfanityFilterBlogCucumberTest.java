package CodeOfConductTest.service;

import dat.entities.BlogPost;
import dat.enums.BlogPostStatus;
import io.cucumber.java.en.*;
import service.ProfanityFilter;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ProfanityFilterBlogCucumberTest {

    private BlogPost blogPost;

    @Given("a blog post with the content {string}")
    public void a_blog_post_with_the_content(String content) {
        blogPost = BlogPost.builder()
                .id(1L)
                .userId(101L)
                .title("") // sættes senere
                .content(content)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(BlogPostStatus.DRAFT)
                .build();
    }

    @And("the title {string}")
    public void the_title(String title) {
        blogPost.setTitle(title);
    }

    @When("the profanity filter is applied")
    public void the_profanity_filter_is_applied() {
        boolean contains = ProfanityFilter.containsProfanity(blogPost);
        blogPost.setStatus(contains ? BlogPostStatus.REJECTED : BlogPostStatus.APPROVED);
    }

    @Then("the blog post should be marked as {word}")
    public void the_blog_post_should_be_marked_as(String expectedStatus) {
        assertEquals(BlogPostStatus.valueOf(expectedStatus), blogPost.getStatus());
    }
}