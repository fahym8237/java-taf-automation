package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class OpenCartWishlistPage extends BasePage {

    private static final By wishlistTable = Locators.css(".table-responsive");
   // private static final By productRows = Locators.css(".table-responsive tbody tr");

    private static final By modelColumn = Locators.xpath("//div[@id='content']");
    private static final By priceColumn = Locators.css("td:nth-child(4)");

   // private static final By addToCartButton = Locators.css("button[data-bs-original-title='Add to Cart']");
    //private static final By removeButton = Locators.css("a[data-bs-original-title='Remove']");
    private static final By wishlistRows = By.cssSelector("#wishlist .table-responsive tbody tr");
    private static final By successAlert = Locators.css(".alert-success");
    private static final By successAlertwish = Locators.xpath("(//div[@id='alert'])[1]");
    private static final By cartButton = Locators.css("#header-cart button");
    private static final By cartItems = Locators.css("#header-cart .dropdown-menu");

    public OpenCartWishlistPage(UiActions ui) {
        super(ui);
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(wishlistTable);
    }

    

    public boolean isModelVisible() {
        return ui.isVisible(modelColumn);
    }

    public boolean isPriceVisible() {
        return ui.isVisible(priceColumn);
    }

    

    public boolean isCartUpdated() {
        return ui.isVisibleNow(successAlert);
    }

    public boolean cartContains(String productName) {
        ui.click(cartButton);
        return ui.text(cartItems).contains(productName);
    }

    /** Click Remove link and wait until it disappears */
    public void removeProduct(String productName) {
        WebElement row = findRowByProduct(productName);

        // Locate the Remove link inside the row
        WebElement removeLink = row.findElement(By.cssSelector("a.btn.btn-danger"));

        // Ensure visibility and clickability
        ui.scrollIntoView(removeLink);
        ui.waitClickable(removeLink);

        // Click with fallback
        try {
            removeLink.click();
        } catch (Exception e) {
            ui.jsClick(removeLink);
        }

        ui.WaitUntilRemoveLinkDisappears(removeLink);
    }



    
    /** Check if product exists in wishlist */
    public boolean containsProduct(String productName) {
        return ui.elements(wishlistRows)
                 .stream()
                 .anyMatch(row -> row.getText().contains(productName));
    }
    
    /** Click Add to Cart button */
    public void addToCart(String productName) {
        WebElement row = findRowByProduct(productName);

        // Locate the button by its form attribute inside the row
        WebElement addButton = row.findElement(By.cssSelector("button[form^='form-product']"));

        // Ensure visibility and clickability
        ui.scrollIntoView(addButton);
        ui.waitClickable(addButton);

        // Use JavaScript click if normal click fails
        try {
            addButton.click();
        } catch (Exception e) {
            ui.jsClick(addButton);
        }
    }

    
    public boolean isProductAddedFromWishlistToCart() {
    	return ui.isVisibleNow(successAlertwish);
    }
    
    /** Helper: find row by product name */
    private WebElement findRowByProduct(String productName) {
        return ui.elements(wishlistRows)
                 .stream()
                 .filter(row -> row.getText().contains(productName))
                 .findFirst()
                 .orElseThrow(() -> new IllegalArgumentException("Product not found in wishlist: " + productName));
    }
}