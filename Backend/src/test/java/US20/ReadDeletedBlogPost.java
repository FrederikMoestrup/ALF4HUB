package US20;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ReadDeletedBlogPost {

        private boolean blogPostExists = false;
        private String errorMessage;

        @Given("a deleted blog post")
        public void aDeletedBlogPost() {
            blogPostExists = false; // Simulate deletion
        }

        @When("I try to open the blog post")
        public void iTryToOpenTheBlogPost() {
            if (!blogPostExists) {
                errorMessage = "Dette opslag findes ikke længere";
            }
        }

        @Then("I should see the message {string}")
        public void iShouldSeeTheMessage(String expectedMessage) {
            Assert.assertEquals(expectedMessage, errorMessage);
        }
}
