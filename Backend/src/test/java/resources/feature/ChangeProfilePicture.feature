Feature: Change Profile Picture

  Scenario: Successfully uploading a new profile picture
    Given I have uploaded a profile picture
    When I save
    Then the profile picture should be updated and displayed correctly across the platform
