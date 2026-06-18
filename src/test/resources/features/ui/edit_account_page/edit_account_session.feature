@ui @edit_account_page @session @opencart
Feature: OpenCart Account - Edit Account Session Management
  As an authenticated user
  I want account updates to persist correctly across my session
  So that my profile data remains accurate after navigation and refresh

  Background:
    Given the user is logged in to edit account
    And the user navigates to the edit account page

  @JAS-61 @JAV-EASE-001 @session @positive @REQ-EASE-001
  Scenario: Updated account data persists after reopening edit account page
    When the user updates the first name on edit account page with a valid value
    And the user updates the last name on edit account page with a valid value
    And the user updates the email on edit account page with a unique valid value
    And the user submits the edit account form
    Then the account information should be updated successfully
    When the user navigates again to the edit account page
    Then the updated first name should be displayed on edit account page
    And the updated last name should be displayed on edit account page
    And the updated email should be displayed on edit account page

  @JAS-62 @JAV-EASE-002 @session @stability @REQ-EASE-002
  Scenario: Refresh edit account page
    When the user refreshes the edit account page
    Then the OpenCart edit account page should be loaded
    And the edit account form should remain usable

  @JAS-60 @JAV-EASE-003 @session @navigation @REQ-EASE-003
  Scenario: Browser back and forward keeps edit account navigation stable
    When the user clicks the back button on edit account page
    Then the my account page should be loaded from edit account flow
    

  @JAS-120 @JAV-EASE-004 @session @access_control @REQ-EASE-004
  Scenario: Unauthenticated user cannot access edit account page directly
    Given the user is not authenticated
    When the user tries to open the OpenCart edit account page directly
    Then the user should be redirected to the login page from edit account flow