Feature: Retrieve user details

  Scenario: A user can retrieve their own information
    Given a user with username "TestUser" and email "test@email.com" exists
    When the user requests their information
    Then the system should return username "TestUser" and email "test@email.com"
