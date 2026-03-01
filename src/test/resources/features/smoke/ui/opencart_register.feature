@ui @smoke @REQ-1002
Feature: OpenCart Registration - Negative Validations
  As a visitor
  I want to register an account
  So that validation errors are shown when mandatory fields are missing

  Scenario: [Negative] Register without filling mandatory fields
    Given the user opens the OpenCart register page
    When the user check the Privacy Policy
    And the user submits the registration form without filling any fields
    Then all mandatory field validation errors should be displayed
