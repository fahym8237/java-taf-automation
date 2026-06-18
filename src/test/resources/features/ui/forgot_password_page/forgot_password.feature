@ui @forgot_password_page @opencart
Feature: OpenCart Authentication - Forgotten Password
  As a visitor
  I want to request a password reset
  So that I can recover my account

  Background:
    Given the user opens the OpenCart forgotten password page

  @JAS-69 @JAV-FP-001 @smoke @REQ-FP-001
  Scenario: Forgotten password page is displayed correctly
    Then the OpenCart forgotten password page should be loaded
    And the forgotten password instruction text should be displayed
    And the email field should be displayed on forgotten password page
    And the continue button should be displayed on forgotten password page
    And the back button should be displayed on forgotten password page

  @JAS-68 @JAV-FP-002 @positive @REQ-FP-002
  Scenario: Submit forgotten password request with registered email
    When the user enters a registered email on forgotten password page
    And the user submits the forgotten password form
    Then the forgotten password request should be accepted

  @JAS-66 @JAV-FP-003 @negative @REQ-FP-003
  Scenario: Submit forgotten password form without email
    When the user submits the forgotten password form without email
    Then the email validation error should be displayed on forgotten password page

  @JAS-64 @JAV-FP-004 @negative @REQ-FP-004
  Scenario: Submit forgotten password form with unregistered email
    When the user enters an unregistered email on forgotten password page
    And the user submits the forgotten password form
    Then the email validation error should be displayed on forgotten password page

  @JAS-65 @JAV-FP-005 @negative @REQ-FP-005
  Scenario: Submit forgotten password form with invalid email format
    When the user enters an invalid email on forgotten password page
    And the user submits the forgotten password form
    Then the email validation error should be displayed on forgotten password page

  @JAS-67 @JAV-FP-006 @navigation @REQ-FP-006
  Scenario: User navigates back to login page from forgotten password page
    When the user clicks the back button on forgotten password page
    Then the OpenCart login page should be loaded