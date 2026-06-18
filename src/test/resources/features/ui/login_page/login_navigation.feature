@ui @login_page @navigation @opencart
Feature: OpenCart Authentication - Login Page Navigation
  As a visitor
  I want to navigate correctly from the login page
  So that I can reach related authentication pages and account links

  Background:
    Given the user opens the OpenCart login page

  @JAS-104 @JAV-LPN-001 @smoke @REQ-LPN-001
  Scenario: User navigates to the forgotten password page from login form
    When the user navigates to the forgotten password page
    Then the forgotten password page should be loaded

  @JAS-107 @JAV-LPN-002 @smoke @REQ-LPN-002
  Scenario: User navigates to the register account page from login page
    When the user navigates to the register account page
    Then the register account page should be loaded

  @JAS-103 @JAV-LPN-003 @navigation @REQ-LPN-003
  Scenario: Breadcrumb is displayed correctly on login page
    Then the login page breadcrumb should display "Login"
    And the login page breadcrumb should display "Account"


  @JAS-105 @JAV-LPN-004 @navigation @REQ-LPN-004
  Scenario: User navigates to register page from right side panel
    When the user clicks the side menu register link on login page
    Then the register account page should be loaded

  @JAS-102 @JAV-LPN-005 @navigation @REQ-LPN-005
  Scenario: User navigates to forgotten password page from right side panel
    When the user clicks the side menu forgotten password link on login page
    Then the forgotten password page should be loaded

  @JAS-106 @JAV-LPN-006 @navigation @REQ-LPN-006
  Scenario: User clicks login self-link from right side panel
    When the user clicks the side menu login link on login page
    Then the OpenCart login page should be loaded