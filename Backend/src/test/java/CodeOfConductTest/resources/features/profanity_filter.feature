Feature: BlogPost should be filtered for profanity

  Scenario: Blog post without profanity is approved
    Given a blog post with the content "Hej alle sammen! Dette er et fredeligt indlæg"
    And the title "Min første blog"
    When the profanity filter is applied
    Then the blog post should be marked as APPROVED

  Scenario: Blog post with profanity is rejected
    Given a blog post with the content "Det her er pis og lort!"
    And the title "Fuck det her"
    When the profanity filter is applied
    Then the blog post should be marked as REJECTED
