Feature: Forum site

Scenario: Forum menu item is visible in the navigation
  Given the user is on the homepage
  When the user views the navigation menu
  Then there should be a visible menu item with the text "Forum"

Scenario: Viewing all blog posts
  Given the user sees a menu item "Forum"
  When the user clicks on the "Forum" menu item
  Then the user should be taken to a page showing an overview of existing blog posts
  And each blog post should display at least a title and a short preview
