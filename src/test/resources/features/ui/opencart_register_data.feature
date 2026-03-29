@JAV-4 @ui @smoke @REQ-1003
Feature: OpenCart Registration - Test Data Injection
  As a tester
  I want to inject generated test data into the registration form
  So that test data layer is validated end-to-end

  Scenario: Fill registration form using generated user data and submit
    Given the user opens the OpenCart register page
    When the user fills the registration form with a generated valid user
    And the user agrees to the privacy policy
    And the user submits the registration form
    And the user accept the registration alert
    Then no mandatory field validation errors should be displayed
   