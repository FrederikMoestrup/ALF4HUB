Feature: Add a strike to a user

  Scenario: Successfully add a strike to a user
    Given a user with username "TestUser" exists
    When the user leaves a team right before tournament start
    Then the user should have 1 strike
