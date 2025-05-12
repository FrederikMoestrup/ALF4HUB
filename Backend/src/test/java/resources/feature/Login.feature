Feature: User Login
  As a registered user
  I want to log in using my email and password
  So that I can access the website and my data

  Scenario: Successful login
    Given I am a registered user
    When I enter a correct email and password
    Then I am logged in and redirected to the homepage

