@ui @login_page @opencart
Feature: OpenCart Authentication - Login Page
  As a visitor or registered customer
  I want to use the login page correctly
  So that I can access my account or navigate to related authentication pages

  Background:
    Given the user opens the OpenCart login page

  @JAS-98 @JAV-LP-001 @smoke @REQ-LP-001
  Scenario: Login page is displayed correctly
    Then the OpenCart login page should be loaded
    And the returning customer section should be displayed
    And the email field should be displayed
    And the password field should be displayed
    And the login button should be displayed
    And the forgotten password link should be displayed

  @JAS-100 @JAV-LP-002 @navigation @REQ-LP-002
  Scenario: User navigates to the forgotten password page
    When the user navigates to the forgotten password page
    Then the forgotten password page should be loaded

  @JAS-93 @JAV-LP-003 @navigation @REQ-LP-003
  Scenario: User navigates to the register account page
    When the user navigates to the register account page
    Then the register account page should be loaded

  @JAS-94 @JAV-LP-004 @negative @REQ-LP-004
  Scenario: Login with empty email and empty password
    When the user submits the login form without credentials
    Then a login warning message should be displayed

  @JAS-91 @JAV-LP-005 @negative @REQ-LP-005
  Scenario: Login with valid email and empty password
    When the user enters a valid login email
    And the user submits the login form without password
    Then a login warning message should be displayed

  @JAS-99 @JAV-LP-006 @negative @REQ-LP-006
  Scenario: Login with empty email and valid password
    When the user enters a valid login password
    And the user submits the login form without email
    Then a login warning message should be displayed

  @JAS-96 @JAV-LP-007 @negative @REQ-LP-007
  Scenario: Login with invalid credentials
    When the user enters an invalid login email
    And the user enters an invalid login password
    And the user submits the login form
    Then a login warning message should be displayed

  @JAS-95 @JAV-LP-008 @negative @REQ-LP-008
  Scenario: Login with unregistered email
    When the user enters an unregistered login email
    And the user enters a valid login password
    And the user submits the login form
    Then a login warning message should be displayed

  @JAS-97 @JAV-LP-009 @positive @REQ-LP-009
  Scenario: Login with valid credentials
    When the user enters a valid login email
    And the user enters a valid login password
    And the user submits the login form
    Then the user should be logged in successfully

  @JAS-92 @JAV-LP-010 @validation @REQ-LP-010
  Scenario: Password field masks typed value
    When the user enters a valid login password
    Then the login password field should mask the entered value