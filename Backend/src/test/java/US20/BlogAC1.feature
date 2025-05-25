Feature: Click blogpost
  As a user
  I want to able to click on a blog post
  So that I can read the full content of the blog post on a separate page

  Scenario: Navigating to a blog post
    Given I see a blog post in the list
    When i click on the blog post
    Then I should be taken to a page with the full content, author and date