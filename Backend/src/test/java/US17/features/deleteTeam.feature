Feature: Delete team

  Scenario: Delete a team after captain confirms the warning message
    Given the team captain confirms the warning message
    Then all players should be removed from the team
    And all associated tournament teams should be removed from the team
    And the team should be permanently deleted from the system