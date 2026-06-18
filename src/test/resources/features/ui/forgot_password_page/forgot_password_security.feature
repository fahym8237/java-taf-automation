@ui @forgot_password_page @security @opencart
Feature: OpenCart Authentication - Forgotten Password Security
  As a visitor
  I want forgotten password security controls to behave correctly
  So that password recovery remains safe and stable

  Background:
    Given the user opens the OpenCart forgotten password page

  @JAS-81 @JAV-FPS-001 @security @REQ-FPS-001
  Scenario: Forgotten password page is served over HTTPS
    Then the forgotten password page URL should use HTTPS

  @JAS-83 @JAV-FPS-002 @security @negative @REQ-FPS-002
  Scenario: Forgotten password form safely handles malicious email input
    When the user enters malicious email input on forgotten password page
    And the user submits the forgotten password form
    Then the forgotten password page should remain stable
    And no JavaScript alert should be displayed on forgotten password page

  @JAS-82 @JAV-FPS-003 @security @negative @REQ-FPS-003
  Scenario: Forgotten password form safely handles very long email input
    When the user enters a very long email on forgotten password page
    And the user submits the forgotten password form
    Then the forgotten password page should remain stable

  @JAS-84 @JAV-FPS-004 @security @stability @REQ-FPS-004
  Scenario: Repeated forgotten password submissions are handled safely
    When the user submits the forgotten password form multiple times with unregistered email
    Then the forgotten password page should remain stable
    And the email validation error should be displayed on forgotten password page