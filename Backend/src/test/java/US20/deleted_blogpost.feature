Feature: Klik på og læs et blogpost

  Scenario: Blogpost findes ikke længere
    Given a deleted blog post
    When I try to open the blog post
    Then I should see the message "Dette opslag findes ikke længere"
