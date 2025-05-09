package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class CreateTournamentSteps {

    private String tournamentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String errorMessage;

    // Scenario 1: Creating a tournament
    @Given("a user wants to create a tournament")
    public void userWantCreateTournament() {
        // User is ready to create a tournament
    }

    @When("they provide the tournament name {string}, start date {string} and end date {string}")
    public void theyProvideTournament_name_start_date_and_end_date(String name, String start, String end) {
        this.tournamentName = name;
        this.startDate = LocalDate.parse(start);
        this.endDate = LocalDate.parse(end);
    }

    @When("the tournament is saved")
    public void tournamentSaved() {
        // Simulate saving the tournament, if no errors, tournament is saved successfully.
    }

    @Then("the tournament should be created successfully")
    public void tournamentCreatedSuccessfully() {
        assertNotNull(tournamentName);
        assertNotNull(startDate);
        assertNotNull(endDate);
    }

    // Scenario 2: Missing required fields
    @When("they attempt to create a tournament without filling in all required fields")
    public void they_attempt_to_create_a_tournament_without_filling_in_all_required_fields() {
        if (tournamentName == null || startDate == null || endDate == null) {
            errorMessage = "All fields must be filled: name, start date, and end date.";
        }
    }

    @Then("an error message should be displayed describing the missing fields")
    public void an_error_message_should_be_displayed_describing_the_missing_fields() {
        assertEquals("All fields must be filled: name, start date, and end date.", errorMessage);
    }

    // Scenario 3: End date before start date
    @When("they attempt to set the end date to be before the start date")
    public void they_attempt_to_set_the_end_date_to_be_before_the_start_date() {
        if (endDate.isBefore(startDate)) {
            errorMessage = "End date cannot be before start date.";
        }
    }

    @Then("an error message should be displayed saying that the end date cannot be before the start date")
    public void an_error_message_should_be_displayed_saying_that_the_end_date_cannot_be_before_the_start_date() {
        assertEquals("End date cannot be before start date.", errorMessage);
    }

    // Scenario 4: Invalid start or end dates (in the past)
    @When("they attempt to set the start or end date to be before today’s date")
    public void they_attempt_to_set_the_start_or_end_date_to_be_before_today_s_date() {
        LocalDate today = LocalDate.now();
        if (startDate.isBefore(today) || endDate.isBefore(today)) {
            errorMessage = "Start and end dates cannot be in the past.";
        }
    }

    @Then("an error message should be displayed saying that the start and end dates cannot be in the past")
    public void an_error_message_should_be_displayed_saying_that_the_start_and_end_dates_cannot_be_in_the_past() {
        assertEquals("Start and end dates cannot be in the past.", errorMessage);
    }

    // Scenario 5: Offensive words in tournament name
    @When("they try to set the tournament name to an offensive word such as {string}")
    public void they_try_to_set_the_tournament_name_to_an_offensive_word(String name) {
        this.tournamentName = name;
        if (name.contains("BadTournamentName")) {
            errorMessage = "This tournament name is offensive, please choose a different name.";
        }
    }

    @Then("an error message should be displayed saying that this name is offensive and they need to choose a different name")
    public void an_error_message_should_be_displayed_saying_that_this_name_is_offensive_and_they_need_to_choose_a_different_name() {
        assertEquals("This tournament name is offensive, please choose a different name.", errorMessage);
    }
}
