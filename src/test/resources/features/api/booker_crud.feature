@JAV-1 @api @smoke @REQ-2001
Feature: Restful-Booker Booking CRUD
  As a tester
  I want to validate booking CRUD operations
  So that API wrappers + domain + test data work end-to-end

  @JAS-1
  Scenario: Create, retrieve, update, patch, and delete a booking
    Given the API client is configured
    And an auth token is available
    When the user creates a new booking
    Then the booking should be created successfully

    When the user retrieves the created booking
    Then the booking details should be returned

    When the user updates the booking using PUT
    Then the booking should be updated successfully

    When the user updates the booking using PATCH
    Then the booking should reflect the patched fields

    When the user deletes the booking
    Then the booking should not be retrievable anymore