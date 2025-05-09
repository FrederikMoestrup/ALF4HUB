Feature: Create a basic tournament

  As a host
  I want to be able to create a new tournament with a name, start date, and end date
  So I can plan an upcoming competition (Tournament)

  Scenario: Creating a tournament
    Given a user wants to create a tournament
    When they provide the tournament name "Summer Cup", start date "2025-06-01" and end date "2025-06-15"
    And the tournament is saved
    Then the tournament should be created successfully

  Scenario: Missing required fields
    Given a user wants to create a tournament
    When they attempt to create a tournament without filling in all required fields
    Then an error message should be displayed describing the missing fields

  Scenario: End date before start date
    Given a user wants to create a tournament
    When they attempt to set the end date to be before the start date
    Then an error message should be displayed saying that the end date cannot be before the start date

  Scenario: Invalid start or end dates
    Given a user wants to create a tournament
    When they attempt to set the start or end date to be before today’s date
    Then an error message should be displayed saying that the start and end dates cannot be in the past

  Scenario: Offensive words in tournament name
    Given a user wants to create a tournament
    When they try to set the tournament name to an offensive word such as "BadTournamentName"
    Then an error message should be displayed saying that this name is offensive and they need to choose a different name
