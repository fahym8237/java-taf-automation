package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartHomePage;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartSearchResultsPage;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartProductPage;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartShoppingCartPage;
import io.cucumber.java.en.*;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartWishlistPage;
import com.fahym.tas.domain.ui.pages.opencart.shop.OpenCartCheckoutPage;
import static org.assertj.core.api.Assertions.assertThat;



public class ShoppingPageSteps {

    private final Config cfg = ConfigLoader.load();

    private OpenCartHomePage homePage;
    private OpenCartSearchResultsPage searchPage;
    private OpenCartProductPage productPage;

    private OpenCartShoppingCartPage cartPage;
    private OpenCartCheckoutPage checkoutPage;
    private OpenCartWishlistPage wishlistPage;
    
    
    
    @Given("the user opens the OpenCart home page")
    public void openHomePage() {
        UiActions ui = new UiActions(cfg.timeout());
        homePage = new OpenCartHomePage(ui).open();
        assertThat(homePage.isLoaded()).isTrue();
    }

    @Then("the OpenCart home page should be loaded")
    public void assertHomePageLoaded() {
        homePage = new OpenCartHomePage(new UiActions(cfg.timeout()));
        assertThat(homePage.isLoaded()).isTrue();
    }

    @Then("the store logo should be displayed")
    public void assertLogoDisplayed() {
        assertThat(homePage.isLogoVisible()).isTrue();
    }

    @Then("the search box should be displayed")
    public void assertSearchBoxDisplayed() {
        assertThat(homePage.isSearchBoxVisible()).isTrue();
    }

    

    @Then("the featured products section should be displayed")
    public void assertFeaturedDisplayed() {
        assertThat(homePage.isFeaturedSectionVisible()).isTrue();
    }

    @When("the user searches for {string}")
    public void searchForProduct(String keyword) {
        searchPage = homePage.search(keyword);
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @When("the user searches with an empty keyword")
    public void searchWithEmptyKeyword() {
        searchPage = homePage.search("");
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @When("the user searches for malicious input")
    public void searchWithMaliciousInput() {
        searchPage = homePage.search("<script>alert('x')</script>");
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @Then("the search results page should be loaded")
    public void assertSearchPageLoaded() {
        searchPage = new OpenCartSearchResultsPage(new UiActions(cfg.timeout()));
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @Then("the search title should display {string}")
    public void assertSearchTitle(String expected) {
        assertThat(searchPage.getTitle()).contains(expected);
    }

    @Then("the search results should contain product {string}")
    public void assertSearchContainsProduct(String product) {
        assertThat(searchPage.containsProduct(product)).isTrue();
    }

    @Then("the system should handle the empty search safely")
    public void assertEmptySearchHandled() {
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @Then("the search results page should remain stable")
    public void assertSearchStable() {
        assertThat(searchPage.isLoaded()).isTrue();
    }

    @Then("no JavaScript alert should be displayed on search results page")
    public void assertNoAlert() {
        assertThat(searchPage.hasBrowserAlert()).isFalse();
    }

    @When("the user opens product {string} from search results")
    public void openProduct(String productName) {
        productPage = searchPage.openProduct(productName);
        assertThat(productPage.isLoaded()).isTrue();
    }

    @Then("the product details page should be loaded")
    public void assertProductPageLoaded() {
        productPage = new OpenCartProductPage(new UiActions(cfg.timeout()));
        assertThat(productPage.isLoaded()).isTrue();
    }

    @Then("the product title should display {string}")
    public void assertProductTitle(String expected) {
        assertThat(productPage.getProductTitle()).contains(expected);
    }
    
    
    @When("the user adds product {string} to the cart from search results")
    public void addProductToCartFromSearchResults(String productName) {
        searchPage.addProductToCart(productName);
    }

    @Then("the cart summary should be updated")
    public void assertCartSummaryUpdated() {
        assertThat(searchPage.isCartSummaryUpdated()).isTrue();
    }

    @Then("the cart should contain product {string}")
    public void assertCartContainsProduct(String productName) {
        assertThat(searchPage.cartDropdownContainsProduct(productName)).isTrue();
    }

    @When("the user opens the shopping cart page")
    public void openShoppingCartPage() {
        cartPage = searchPage.openShoppingCartPage();
        assertThat(cartPage.isLoaded()).isTrue();
    }

    @Then("the shopping cart page should be loaded")
    public void assertShoppingCartPageLoaded() {
        cartPage = new OpenCartShoppingCartPage(new UiActions(cfg.timeout()));
        assertThat(cartPage.isLoaded()).isTrue();
    }

    @Then("the shopping cart should contain product {string}")
    public void assertShoppingCartContainsProduct(String productName) {
        assertThat(cartPage.containsProduct(productName)).isTrue();
    }

    @Then("the cart totals section should be displayed")
    public void assertCartTotalsSectionDisplayed() {
        assertThat(cartPage.isTotalsSectionVisible()).isTrue();
    }

    @When("the user updates cart product {string} quantity to {string}")
    public void updateCartProductQuantity(String productName ,String quantity) {
        //cartPage.updateQuantity(quantity);
    	cartPage.updateProductQuantity(productName, quantity);
    }

    @Then("the shopping cart should display quantity {string}")
    public void assertShoppingCartQuantity(String expectedQuantity) {
        assertThat(cartPage.quantityValue()).isEqualTo(expectedQuantity);
    }

    @Then("the cart total should be recalculated")
    public void assertCartTotalRecalculated() {
        assertThat(cartPage.isTotalVisible()).isTrue();
    }

    @When("the user removes product {string} from the cart")
    public void removeProductFromCart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("the shopping cart should not contain product {string}")
    public void assertShoppingCartDoesNotContainProduct(String productName) {
        assertThat(cartPage.isProductRemoved(productName)).isTrue();
    }

    @When("the user clicks continue shopping")
    public void clickContinueShopping() {
        homePage = cartPage.continueShopping();
        assertThat(homePage.isLoaded()).isTrue();
    }
    
    @When("the user adds product {string} to the wishlist from search results")
    public void addProductToWishlist(String productName) {
        searchPage.addProductToWishlist(productName);
    }

    @Then("the wishlist counter should be updated")
    public void assertWishlistCounterUpdated() {
        assertThat(searchPage.isWishlistUpdated()).isTrue();
    }

    @Then("a wishlist success message should be displayed")
    public void assertWishlistSuccessMessage() {
        assertThat(searchPage.isWishlistSuccessVisible()).isTrue();
    }

    @When("the user opens the wishlist page")
    public void openWishlistPage() {
        wishlistPage = searchPage.openWishlistPage();
        assertThat(wishlistPage.isLoaded()).isTrue();
    }

    @Then("the wishlist page should be loaded")
    public void assertWishlistPageLoaded() {
        wishlistPage = new OpenCartWishlistPage(new UiActions(cfg.timeout()));
        assertThat(wishlistPage.isLoaded()).isTrue();
    }

    @Then("the wishlist should contain product {string}")
    public void assertWishlistContainsProduct(String product) {
        assertThat(wishlistPage.containsProduct(product)).isTrue();
    }

    @Then("the wishlist product model should be displayed")
    public void assertWishlistModelDisplayed() {
        assertThat(wishlistPage.isModelVisible()).isTrue();
    }

    @Then("the wishlist product price should be displayed")
    public void assertWishlistPriceDisplayed() {
        assertThat(wishlistPage.isPriceVisible()).isTrue();
    }

    @When("the user adds wishlist product {string} to the cart")
    public void addWishlistProductToCart(String product) {
        wishlistPage.addToCart(product);
        //assertThat(wishlistPage.isProductAddedFromWishlistToCart()).isTrue();
    }

    @Then("the cart summary should be updated from wishlist")
    public void assertCartUpdatedFromWishlist() {
        assertThat(wishlistPage.isCartUpdated()).isTrue();
    }

    @Then("the cart should contain product {string} from wishlist flow")
    public void assertCartContainsFromWishlist(String product) {
    	
        assertThat(searchPage.cartDropdownContainsProduct(product)).isTrue();
    }

    @When("the user removes product {string} from wishlist")
    public void removeFromWishlist(String product) {
        wishlistPage.removeProduct(product);
    }

    @Then("the wishlist should not contain product {string}")
    public void assertWishlistNotContains(String product) {
        assertThat(wishlistPage.containsProduct(product)).isFalse();
    }

    @Then("the user should be redirected to the login page or authentication warning should be displayed")
    public void assertRedirectOrWarning() {
        
        assertThat(searchPage.isWishlistWarningVisible()).isTrue();
    }
    
    
    
    
    
    @Given("the cart contains product {string}")
    public void ensureCartContainsProduct(String product) {
        searchPage = homePage.search("MacBook");
        searchPage.addProductToCart(product);
        assertThat(searchPage.isCartSummaryUpdated()).isTrue();
    }

    @When("the user clicks checkout")
    public void clickCheckout() {
        checkoutPage = cartPage.goToCheckout();
        assertThat(checkoutPage.isLoaded()).isTrue();
    }

    @Then("the checkout page should be loaded")
    public void assertCheckoutPageLoaded() {
        checkoutPage = new OpenCartCheckoutPage(new UiActions(cfg.timeout()));
        assertThat(checkoutPage.isLoaded()).isTrue();
    }

    @Then("the checkout heading should display {string}")
    public void assertCheckoutHeading(String expected) {
        assertThat(checkoutPage.getHeading()).contains(expected);
    }

    @Then("the shipping address section should be displayed")
    public void assertShippingSectionVisible() {
        assertThat(checkoutPage.isShippingSectionVisible()).isTrue();
    }

    @When("the user opens the checkout page")
    public void openCheckoutPageDirect() {
        checkoutPage = new OpenCartCheckoutPage(new UiActions(cfg.timeout())).open();
        assertThat(checkoutPage.isLoaded()).isTrue();
    }
    
    
    //Shipping fields checks
    @Then("the shipping first name field should be displayed")
    public void assertFirstNameField() {
        assertThat(checkoutPage.isFirstNameVisible()).isTrue();
    }

    @Then("the shipping last name field should be displayed")
    public void assertLastNameField() {
        assertThat(checkoutPage.isLastNameVisible()).isTrue();
    }

    @Then("the shipping address field should be displayed")
    public void assertAddressField() {
        assertThat(checkoutPage.isAddressVisible()).isTrue();
    }

    @Then("the shipping city field should be displayed")
    public void assertCityField() {
        assertThat(checkoutPage.isCityVisible()).isTrue();
    }

    @Then("the shipping postcode field should be displayed")
    public void assertPostcodeField() {
        assertThat(checkoutPage.isPostcodeVisible()).isTrue();
    }

    @Then("the shipping country dropdown should be displayed")
    public void assertCountryField() {
        assertThat(checkoutPage.isCountryVisible()).isTrue();
    }

    @Then("the shipping region dropdown should be displayed")
    public void assertRegionField() {
        assertThat(checkoutPage.isRegionVisible()).isTrue();
    }
    
    //Form submission
    @When("the user submits the empty shipping address form")
    public void submitEmptyShippingForm() {
        checkoutPage.submitShipping();
    }

    @Then("shipping address validation errors should be displayed")
    public void assertShippingErrors() {
    	assertThat(checkoutPage.isFirstNameErrorVisible()).isTrue();
    	assertThat(checkoutPage.isLastNameErrorVisible()).isTrue();
    	assertThat(checkoutPage.isAddressErrorVisible()).isTrue();
    	assertThat(checkoutPage.isCityErrorVisible()).isTrue();
    	assertThat(checkoutPage.isRegionErrorVisible()).isTrue();
    	assertThat(checkoutPage.isPostcodeErrorVisible()).isTrue();
    	
    }
    
    //Valid data
    
    @When("the user fills the shipping address form with valid data")
    public void fillShippingForm() {
        checkoutPage.fillValidAddress();
    }

    @When("the user saves the shipping address")
    public void saveShippingAddress() {
        checkoutPage.submitShipping();
    }

    @Then("the shipping address should be accepted")
    public void assertShippingAccepted() {
        assertThat(checkoutPage.isShippingAccepted()).isTrue();
    }
    
    //Access control
    
    @When("the user opens the checkout page directly")
    public void openCheckoutDirectly() {
        checkoutPage = new OpenCartCheckoutPage(new UiActions(cfg.timeout())).open();
       
    }
    @When("the user select I want to use a new address")
    public void selectNewAddress() {
    	checkoutPage.selectNewAddress();
    }

    @Then("the shopping cart should be empty")
    public void assertCheckoutRedirect() {
        
        assertThat(checkoutPage.shoppingCartIsEmpty()).isTrue();
    }
}