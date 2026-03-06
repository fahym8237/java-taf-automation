@ui @smoke @regression @REQ-1001
Feature: OpenCart Authentication - Forgotten Password
  As a visitor
  I want to request a password reset
  So that I can recover my account

  Scenario: [Negative] Forgotten password requires email
    Given the user opens the OpenCart login page
    When the user navigates to the forgotten password page
    And the user submits the forgotten password form without email
    Then the email validation error should be displayed
