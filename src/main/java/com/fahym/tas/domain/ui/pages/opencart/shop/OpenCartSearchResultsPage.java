package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OpenCartSearchResultsPage extends BasePage {

    private static final By title = Locators.css("#content h1");
    private static final By products = Locators.css(".product-thumb h4 a");

    //private static final By addToCartButtons = Locators.css(".product-thumb button[onclick*='cart.add']");
    private static final By successAlert = Locators.css(".alert-success");
    private static final By cartButton = Locators.css(".btn.btn-lg.btn-dark.d-block.dropdown-toggle");
    private static final By cartDropdownItems = Locators.css(".dropdown-menu.dropdown-menu-end.p-2.show");
    private static final By viewCartLink = Locators.xpath("//strong[contains(text(),'View Cart')]/..");
   // private static final By wishlistButtons = Locators.css(".product-thumb button[onclick*='wishlist.add']");
    private static final By wishlistSuccess = Locators.css(".alert-success");
    private static final By wishlistLink = Locators.xpath("//a[contains(@href,'account/wishlist')]");
    private static final By wishlisAlert = Locators.xpath("//div[@id='alert']");
    public OpenCartSearchResultsPage(UiActions ui) {
        super(ui);
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(title);
    }

    public String getTitle() {
        return ui.text(title);
    }

    public boolean containsProduct(String name) {
        return ui.elements(products)
                .stream()
                .map(WebElement::getText)
                .anyMatch(p -> p.contains(name));
    }

    public OpenCartProductPage openProduct(String name) {
        WebElement productElement = ui.elements(products)
                .stream()
                .filter(e -> e.getText().contains(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + name));

        ui.scrollIntoViewAndClick(productElement);

        return new OpenCartProductPage(ui);
    }




   

	public boolean hasBrowserAlert() {
        return ui.hasBrowserAlert();
    }
	
	public void addProductToCart(String productName) {
    // Find the product container by name
    WebElement productContainer = ui.elements(By.cssSelector(".product-thumb"))
            .stream()
            .filter(container -> container.getText().contains(productName))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));

    // Scroll the container into view
    ui.scrollIntoView(productContainer);

    // Locate the Add to Cart button inside this container
    WebElement addToCartButton = productContainer.findElement(
            By.cssSelector("div.button button[formaction*='checkout/cart.add']")
    );

    // Scroll and click the button
    ui.scrollIntoViewAndClick(addToCartButton);
}


	public boolean isCartSummaryUpdated() {
	    return ui.isVisibleNow(successAlert) || ui.isVisible(successAlert);
	}

	public boolean cartDropdownContainsProduct(String productName) {
	    ui.click(cartButton);
	    return ui.elements(cartDropdownItems)
	            .stream()
	            .anyMatch(e -> e.getText().contains(productName));
	}

	public OpenCartShoppingCartPage openShoppingCartPage() {
	    ui.click(cartButton);
	    ui.click(viewCartLink);
	    return new OpenCartShoppingCartPage(ui);
	}
	
	public void addProductToWishlist(String productName) {
		// Find the product container by name
	    WebElement productContainer = ui.elements(By.cssSelector(".product-thumb"))
	            .stream()
	            .filter(container -> container.getText().contains(productName))
	            .findFirst()
	            .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));

	    // Scroll the container into view
	    ui.scrollIntoView(productContainer);

	    // Locate the Add to Cart button inside this container
	    WebElement addToCartButton = productContainer.findElement(
	            By.cssSelector("div.button button[formaction*='account/wishlist.add']")
	    );

	    // Scroll and click the button
	    ui.scrollIntoViewAndClick(addToCartButton);
	}

	public boolean isWishlistUpdated() {
	    return ui.isVisibleNow(wishlistSuccess) || ui.isVisible(wishlistSuccess);
	}

	public boolean isWishlistSuccessVisible() {
	    return ui.isVisible(wishlistSuccess);
	}

	public OpenCartWishlistPage openWishlistPage() {
	    ui.click(wishlistLink);
	    return new OpenCartWishlistPage(ui);
	}

	public boolean isWishlistWarningVisible() {
	    return ui.isVisible(wishlisAlert); // login redirect message also appears here
	}
}