@ui @edit_account_page @security @opencart
Feature: OpenCart Account - Edit Account Security
  As an authenticated user
  I want edit account security controls to behave correctly
  So that my account information remains protected

  Background:
    Given the user is logged in to edit account
    And the user navigates to the edit account page

  @JAS-57 @JAV-EAS-001 @security @REQ-EAS-001
  Scenario: Edit account page is served over HTTPS
    Then the edit account page URL should use HTTPS

  @JAS-55 @JAV-EAS-002 @security @negative @REQ-EAS-002
  Scenario: Edit account form safely handles malicious input
    When the user enters malicious first name input on edit account page
    And the user enters malicious last name input on edit account page
    And the user enters malicious email input on edit account page
    And the user submits the edit account form
    Then the edit account page should remain stable

  @JAS-58 @JAV-EAS-003 @security @negative @REQ-EAS-003
  Scenario: Edit account form safely handles very long values
    When the user enters a very long first name on edit account page
    And the user enters a very long last name on edit account page
    And the user enters a very long email on edit account page
    And the user submits the edit account form
    Then the edit account page should remain stable

  @JAS-120 @JAV-EAS-004 @security @access_control @REQ-EAS-004
  Scenario: Unauthenticated user cannot access edit account page directly
    Given the user is not authenticated
    When the user tries to open the OpenCart edit account page directly
    Then the user should be redirected to the login page from edit account flow