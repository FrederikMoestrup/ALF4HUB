Feature: Blog Post Management
  Scenario: Creating a blog post
    Given a user wants to create a blog post
    When they provide the title "My First Blog Post" and content "This is the content of my first blog post."
    And the blog post is saved
    Then the post should be created successfully

  Scenario: Retrieving a blog post by ID
    Given a blog post exists with title "Another Blog Post" and content "This is the content of another blog post."
    When I retrieve the blog post by ID
    Then the title should be "Another Blog Post"
    And the content should be "This is the content of another blog post."

  Scenario: Retrieving all blog posts
    Given multiple blog posts exist
    When I retrieve all blog posts
    Then I should see a list of all blog posts

  Scenario: Retrieve all blogs but only with preview of content
    Given multiple blog posts exist
    When I retrieve all blog posts with only preview of content
    Then I should see a list of all blog posts with only a preview of their content

  Scenario: User can edit the title and content of an existing blog post
    Given the user is on their blog section and selects an existing blog post
    And clicks the edit button
    When the user edits the title and content of the blog post and clicks Save
    Then the changes are successfully saved
    And the user receives a confirmation message: "Blogpostet er opdateret succesfuldt."
    And the timestamp of the last edit is updated and shown to the user
