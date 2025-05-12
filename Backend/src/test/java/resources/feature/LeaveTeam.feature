Feature: Leave Tournament Team
  As a user
  I want to be able to leave my tournament team
  So that I can join another team or withdraw from the tournament

  Scenario: User chooses to leave the team
    Given I am registered on a team
    And I navigate to the team page
    And the tournament has not yet started
    When I click the "Leave Team" button
    Then I am removed from the team
    And I receive a confirmation message on the screen and/or via email
