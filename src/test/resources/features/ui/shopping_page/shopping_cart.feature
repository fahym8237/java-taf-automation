@ui @shopping_cart @opencart
Feature: OpenCart Shopping - Shopping Cart
  As a customer
  I want to manage products in my shopping cart
  So that I can review products before checkout

  Background:
    Given the user opens the OpenCart home page

  @JAS-162 @JAV-CART-001 @smoke @REQ-CART-001
  Scenario: Add MacBook Air to cart from search results
    When the user searches for "MacBook"
    And the user adds product "MacBook Air" to the cart from search results
    Then the cart summary should be updated
    And the cart should contain product "MacBook Air"

  @JAS-163 @JAV-CART-002 @cart @REQ-CART-002
  Scenario: Open shopping cart page after adding product
    When the user searches for "MacBook"
    And the user adds product "MacBook Air" to the cart from search results
    And the user opens the shopping cart page
    Then the shopping cart page should be loaded
    And the shopping cart should contain product "MacBook Air"
    And the cart totals section should be displayed

  @JAS-166 @JAV-CART-003 @cart @REQ-CART-003
  Scenario: Update product quantity in shopping cart
    When the user searches for "MacBook"
    And the user adds product "MacBook Air" to the cart from search results
    And the user opens the shopping cart page
    And the user updates cart product "MacBook Air" quantity to "2"
    Then the shopping cart should display quantity "2"
    And the cart total should be recalculated

  @JAS-164 @JAV-CART-004 @cart @REQ-CART-004
  Scenario: Remove product from shopping cart
    When the user searches for "MacBook"
    And the user adds product "MacBook Air" to the cart from search results
    And the user opens the shopping cart page
    And the user removes product "MacBook Air" from the cart
    Then the shopping cart should not contain product "MacBook Air"

  @JAS-165 @JAV-CART-005 @navigation @REQ-CART-005
  Scenario: Continue shopping from cart page
    When the user searches for "MacBook"
    And the user adds product "MacBook Air" to the cart from search results
    And the user opens the shopping cart page
    And the user clicks continue shopping
    Then the OpenCart home page should be loaded