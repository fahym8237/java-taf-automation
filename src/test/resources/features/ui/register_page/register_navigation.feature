@ui @register_page @navigation @opencart
Feature: OpenCart Authentication - Register Page Navigation
  As a visitor
  I want to navigate correctly from the register page
  So that I can reach related authentication pages

  Background:
    Given the user opens the OpenCart register page

  @JAV-RPN-001 @smoke @REQ-RPN-001
  Scenario: User navigates to login page from register intro link
    When the user clicks the login link on register page
    Then the OpenCart login page should be loaded from register flow

  @JAV-RPN-002 @navigation @REQ-RPN-002
  Scenario: Breadcrumb is displayed correctly on register page
    Then the register page breadcrumb should display "Account"
    And the register page breadcrumb should display "Register"

  @JAV-RPN-003 @navigation @REQ-RPN-003
  Scenario: User navigates to login page from side menu
    When the user clicks the side menu login link on register page
    Then the OpenCart login page should be loaded from register flow

  @JAV-RPN-004 @navigation @REQ-RPN-004
  Scenario: User clicks register self-link from side menu
    When the user clicks the side menu register link on register page
    Then the OpenCart register page should be loaded

  @JAV-RPN-005 @navigation @REQ-RPN-005
  Scenario: User navigates to forgotten password page from side menu
    When the user clicks the side menu forgotten password link on register page
    Then the OpenCart forgotten password page should be loaded from register flow

  @JAV-RPN-006 @navigation @REQ-RPN-006
  Scenario: User opens privacy policy from register page
    When the user clicks the privacy policy link on register page
    Then the privacy policy should be opened from register page