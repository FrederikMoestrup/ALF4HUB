Feature: Confirmation Email

  Scenario: Successfully leaving a tournament team triggers confirmation email
    Given I am part of tournament team "Team K"
    When I leave tournament team "Team K"
    Then an email should be sent to my registered email address
    And the email should contain a confirmation message "You have successfully left the tournament team Team K."
