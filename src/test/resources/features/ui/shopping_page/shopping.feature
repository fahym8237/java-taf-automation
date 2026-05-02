@ui @shopping_page @opencart
Feature: OpenCart Shopping - Home and Product Search
  As a customer
  I want to browse and search products
  So that I can find products before buying them

  Background:
    Given the user opens the OpenCart home page

  @JAV-SHOP-001 @smoke @REQ-SHOP-001
  Scenario: Shopping home page is displayed correctly
    Then the OpenCart home page should be loaded
    And the store logo should be displayed
    And the search box should be displayed
    And the featured products section should be displayed

  @JAV-SHOP-002 @search @smoke @REQ-SHOP-002
  Scenario: Search for MacBook product
    When the user searches for "MacBook"
    Then the search results page should be loaded
    And the search title should display "Search - MacBook"
    And the search results should contain product "MacBook"
    And the search results should contain product "MacBook Air"
    And the search results should contain product "MacBook Pro"

  @JAV-SHOP-003 @search @negative @REQ-SHOP-003
  Scenario: Search with empty keyword
    When the user searches with an empty keyword
    Then the search results page should be loaded
    And the system should handle the empty search safely

  @JAV-SHOP-004 @search @security @REQ-SHOP-004
  Scenario: Search with malicious keyword
    When the user searches for malicious input
    Then the search results page should remain stable
    And no JavaScript alert should be displayed on search results page

  @JAV-SHOP-005 @navigation @REQ-SHOP-005
  Scenario: Open product details from search results
    When the user searches for "MacBook"
    And the user opens product "MacBook Air" from search results
    Then the product details page should be loaded
    And the product title should display "MacBook Air"