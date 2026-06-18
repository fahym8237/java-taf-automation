@ui @login_page @session @opencart
Feature: OpenCart Authentication - Login Page Session Management
  As a user
  I want authentication session behavior to work correctly
  So that protected account pages are accessed only when authorized

  Background:
    Given the user opens the OpenCart login page

  @JAS-117 @JAV-LPSE-001 @session @positive @REQ-LPSE-001
  Scenario: User reaches account page after successful login
    When the user enters a valid login email
    And the user enters a valid login password
    And the user submits the login form
    Then the user should be logged in successfully
    And the my account page should be loaded

  @JAS-118 @JAV-LPSE-002 @session @access_control @REQ-LPSE-002
  Scenario: Unauthenticated user cannot access My Account page directly
    When the user opens the OpenCart my account page directly
    Then the user should be redirected to the login page

  @JAS-120 @JAV-LPSE-003 @session @access_control @REQ-LPSE-003
  Scenario: Unauthenticated user cannot access Edit Account page directly
    When the user opens the OpenCart edit account page directly
    Then the user should be redirected to the login page

  @JAS-116 @JAV-LPSE-004 @session @access_control @REQ-LPSE-004
  Scenario: Unauthenticated user cannot access Change Password page directly
    When the user opens the OpenCart change password page directly
    Then the user should be redirected to the login page

  @JAS-121 @JAV-LPSE-005 @session @positive @REQ-LPSE-005
  Scenario: Authenticated user can logout successfully
    When the user enters a valid login email
    And the user enters a valid login password
    And the user submits the login form
    Then the user should be logged in successfully
    When the user logs out from the account area
    Then the user should be logged out successfully

  @JAS-119 @JAV-LPSE-006 @session @positive @REQ-LPSE-006
  Scenario: Authenticated user opens login page again
    When the user enters a valid login email
    And the user enters a valid login password
    And the user submits the login form
    Then the user should be logged in successfully
    When the user opens the OpenCart login page again
    Then the application should handle the authenticated login-page access correctly