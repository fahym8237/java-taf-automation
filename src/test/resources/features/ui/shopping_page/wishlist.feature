@ui @wishlist @opencart
Feature: OpenCart Shopping - Wishlist
  As an authenticated customer
  I want to add products to my wishlist
  So that I can save products for later

  Background:
    Given the user is logged in
    And the user opens the OpenCart home page

  @JAV-WISH-001 @smoke @REQ-WISH-001
  Scenario: Add MacBook Pro to wishlist from search results
    When the user searches for "MacBook"
    And the user adds product "MacBook Pro" to the wishlist from search results
    Then the wishlist counter should be updated
    And a wishlist success message should be displayed

  @JAV-WISH-002 @wishlist @REQ-WISH-002
  Scenario: Verify My Wishlist page after adding product
    When the user searches for "MacBook"
    And the user adds product "MacBook Pro" to the wishlist from search results
    And the user opens the wishlist page
    Then the wishlist page should be loaded
    And the wishlist should contain product "MacBook Pro"
    And the wishlist product model should be displayed
    

  @JAV-WISH-003 @wishlist @REQ-WISH-003
  Scenario: Add wishlist product to cart
    When the user searches for "MacBook"
    And the user adds product "MacBook Pro" to the wishlist from search results
    And the user opens the wishlist page
    And the user adds wishlist product "MacBook Pro" to the cart
    Then the cart should contain product "MacBook Pro" from wishlist flow

  @JAV-WISH-004 @wishlist @REQ-WISH-004
  Scenario: Remove product from wishlist
    When the user searches for "MacBook"
    And the user adds product "MacBook Pro" to the wishlist from search results
    And the user opens the wishlist page
    And the user removes product "MacBook Pro" from wishlist
    Then the wishlist should not contain product "MacBook Pro"

  @JAV-WISH-005 @access_control @REQ-WISH-005
  Scenario: Unauthenticated user is redirected or warned when adding product to wishlist
    Given the user is not authenticated
    And the user opens the OpenCart home page
    When the user searches for "MacBook"
    And the user adds product "MacBook Pro" to the wishlist from search results
    Then the user should be redirected to the login page or authentication warning should be displayed