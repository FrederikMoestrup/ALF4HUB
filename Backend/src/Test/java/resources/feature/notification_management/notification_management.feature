Feature: Notification management

  Background:
    Given a user "lars" with password "test123" and email "lars@example.com" exists
    And all notifications are deleted

  Scenario: Create a notification
    Given a notification with type INFO and title "Test notification" and unread status
    When the notification is created for user "lars"
    Then the notification id should be positive
    And the notification title should be "Test notification"
    And the notification should be unread

  Scenario: Get notification by ID
    Given a notification with type INFO and title "Find me" and unread status
    And the notification is created for user "lars"
    When I get the notification by id
    Then the notification title should be "Find me"

  Scenario: Update a notification
    Given a notification with type INFO and title "Update me" and unread status
    And the notification is created for user "lars"
    When the notification is updated to title "Updated title" and read status
    Then the notification title should be "Updated title"
    And the notification should be read

  Scenario: Delete a notification
    Given a notification with type INFO and title "Delete me" and unread status
    And the notification is created for user "lars"
    When the notification is deleted
    Then getting the notification by id should fail

  Scenario: Count unread and total notifications for user
    Given notifications:
      | type | title    | read  |
      | INFO | Unread 1 | false |
      | INFO | Unread 2 | false |
      | INFO | Read     | true  |
    When notifications are created for user "lars"
    Then the unread count for user "lars" should be 2
    And the total notification count for user "lars" should be 3

  Scenario: Mark a notification as read
    Given a notification with type INFO and title "Mark read test" and unread status
    And the notification is created for user "lars"
    When the notification is marked as read
    Then the notification should be read

  Scenario: Mark all notifications as read for a user
    Given notifications:
      | type | title    | read  |
      | INFO | Unread 1 | false |
      | INFO | Unread 2 | false |
    When notifications are created for user "lars"
    And all notifications are marked as read for user "lars"
    Then the unread count for user "lars" should be 0

  Scenario: Get all notifications for user
    Given notifications:
      | type | title  | read  |
      | INFO | Note 1 | false |
      | INFO | Note 2 | false |
    When notifications are created for user "lars"
    And I get all notifications for user "lars"
    Then the notification list size should be 2
    And the first notification title should be "Note 2"

  Scenario: Invitation-notifikationer returneres korrekt
    Given a user "lars" with password "test123" and email "lars@example.com" exists
    And all notifications are deleted
    And notifications:
      | type       | title                 | read  |
      | INVITATION | Du er inviteret til X | false |
      | INFO       | Systembesked          | false |
    When notifications are created for user "lars"
    And I get all INVITATION notifications for user "lars"
    Then the notification list size should be 1
    And the first notification title should be "Du er inviteret til X"

  Scenario: Request-notifikationer returneres korrekt
    Given a user "lars" with password "test123" and email "lars@example.com" exists
    And all notifications are deleted
    And notifications:
      | type   | title              | read  |
      | REQUEST | Du har ansøgt     | false |
      | INFO    | Andet info        | false |
    When notifications are created for user "lars"
    And I get all REQUEST notifications for user "lars"
    Then the notification list size should be 1
    And the first notification title should be "Du har ansøgt"

  Scenario: Info-notifikationer returneres korrekt
    Given a user "lars" with password "test123" and email "lars@example.com" exists
    And all notifications are deleted
    And notifications:
      | type | title            | read  |
      | INFO | System opdateret | false |
      | MESSAGE | Besked        | false |
    When notifications are created for user "lars"
    And I get all INFO notifications for user "lars"
    Then the notification list size should be 1
    And the first notification title should be "System opdateret"

  Scenario: Message-notifikationer returneres korrekt
    Given a user "lars" with password "test123" and email "lars@example.com" exists
    And all notifications are deleted
    And notifications:
      | type    | title          | read  |
      | MESSAGE | Du har en besked | false |
      | INFO    | Generel info     | false |
    When notifications are created for user "lars"
    And I get all MESSAGE notifications for user "lars"
    Then the notification list size should be 1
    And the first notification title should be "Du har en besked"

