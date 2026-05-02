@ui @checkout @opencart
Feature: OpenCart Shopping - Checkout
  As an authenticated customer
  I want to proceed to checkout
  So that I can complete my order

  Background:
    Given the user is logged in
    And the user opens the OpenCart home page
    And the cart contains product "MacBook Air"

  @JAV-CHK-001 @smoke @REQ-CHK-001
  Scenario: Open checkout page from shopping cart
    When the user opens the shopping cart page
    And the user clicks checkout
    Then the checkout page should be loaded
    And the checkout heading should display "Checkout"
	And the user select I want to use a new address
    And the shipping address section should be displayed

  @JAV-CHK-002 @checkout @REQ-CHK-002
  Scenario: Verify checkout page required shipping fields
    When the user opens the checkout page
	And the user select I want to use a new address
    Then the shipping first name field should be displayed
    And the shipping last name field should be displayed
    And the shipping address field should be displayed
    And the shipping city field should be displayed
    And the shipping postcode field should be displayed
    And the shipping country dropdown should be displayed
    And the shipping region dropdown should be displayed

  @JAV-CHK-003 @checkout @negative @REQ-CHK-003
  Scenario: Submit checkout shipping address with empty required fields
    When the user opens the checkout page
	And the user select I want to use a new address
    And the user submits the empty shipping address form
    Then shipping address validation errors should be displayed

  @JAV-CHK-004 @checkout @positive @REQ-CHK-004
  Scenario: Fill checkout shipping address with valid data
    When the user opens the checkout page
	And the user select I want to use a new address
    And the user fills the shipping address form with valid data
    And the user saves the shipping address
    Then the shipping address should be accepted

  @JAV-CHK-005 @access_control @REQ-CHK-005
  Scenario: Unauthenticated user cannot access checkout directly
    Given the user is not authenticated
    When the user opens the checkout page directly
    Then the shopping cart should be empty