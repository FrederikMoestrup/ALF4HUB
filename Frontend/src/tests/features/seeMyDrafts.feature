Feature: See My Drafts

Scenario: Button to view drafts is shown on the Forum page
  Given the user is on the Forum page
  When the page loads
  Then there should be a visible button with the text "See your drafts"

Scenario: Viewing saved drafts for a user
  Given the user is logged in and on the Forum page
  When the user clicks the button "See your drafts"
  Then the user should be taken to a page with their saved drafts
  And each draft should display at least a title and a creation or last modified date
