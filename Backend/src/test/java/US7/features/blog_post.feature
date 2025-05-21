Feature: Blog Post Management

  Scenario: User can edit the title and content of an existing blog post
    Given the user is on their blog section and selects an existing blog post
    And clicks the edit button
    When the user edits the title and content of the blog post and clicks Save
    Then the changes are successfully saved
    And the user receives a confirmation message: "Blogpostet er opdateret succesfuldt."
    And the timestamp of the last edit is updated and shown to the user
