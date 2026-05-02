package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class OpenCartShoppingCartPage extends BasePage {

    
  
    private static final By quantityInput = Locators.css("input[name*='quantity']");
    private static final By updateButton = Locators.css("button[data-bs-original-title='Update']");
    //private static final By removeButton = Locators.css("button[data-bs-original-title='Remove']");
    private static final By cartTable = Locators.css(".table-responsive");
    private static final By productRows = Locators.css(".table-responsive tbody tr");
    private static final By totalsSection = Locators.css("#checkout-total");
    private static final By subTotalValue = Locators.xpath("//tfoot[@id='checkout-total']//tr[1]//td[@class='text-end']");
    private static final By totalValue = Locators.xpath("//tfoot[@id='checkout-total']//tr[2]//td[@class='text-end']");
    private static final By continueShoppingButton = Locators.xpath("//a[text()='Continue Shopping']");
    private static final By checkoutButton = Locators.xpath("//a[@title='Checkout']//i[@class='fa-solid fa-share']");
    
    
    
    public OpenCartShoppingCartPage(UiActions ui) {
        super(ui);
    }

    /** Check if product exists in the cart */
    public boolean containsProduct(String productName) {
        return ui.elements(productRows)
                 .stream()
                 .anyMatch(row -> row.getText().contains(productName));
    }

   


    public boolean isTotalsSectionVisible() {
        return ui.isVisible(totalsSection);
    }

    public void updateQuantity(String quantity) {
        ui.clear(quantityInput);
        ui.type(quantityInput, quantity);
        ui.click(updateButton);
    }

    public String quantityValue() {
        String value = ui.attribute(quantityInput, "value");
        return value == null ? "" : value;
    }

    public boolean isTotalVisible() {
        return ui.isVisible(totalValue);
    }

    
    @Override
    public boolean isLoaded() {
        return ui.isVisible(cartTable);
    }

    /** Helper: find row by product name */
    private WebElement getProductRow(String productName) {
        return ui.elements(productRows)
                .stream()
                .filter(row -> row.getText().contains(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    /** Update quantity for a given product */
    public void updateProductQuantity(String productName, String newQuantity) {
        WebElement row = getProductRow(productName);

        WebElement quantityInput = row.findElement(By.cssSelector("input[name='quantity']"));
        quantityInput.clear();
        quantityInput.sendKeys(newQuantity);

        // More robust locator
        WebElement updateBtn = row.findElement(By.xpath(".//button[.//i[contains(@class,'fa-rotate')]]"));
        ui.click(updateBtn);
    }



    /** Get Unit Price for a product */
    public String getUnitPrice(String productName) {
        WebElement row = getProductRow(productName);
        return row.findElements(By.cssSelector("td.text-end")).get(0).getText();
    }

    /** Get Total Price for a product */
    public String getTotalPrice(String productName) {
        WebElement row = getProductRow(productName);
        return row.findElements(By.cssSelector("td.text-end")).get(1).getText();
    }

    /** Remove product by name */
    public void removeProduct(String productName) {
        WebElement row = getProductRow(productName);

        // Option A: match by aria-label
        //WebElement removeBtn = row.findElement(By.cssSelector("a.btn.btn-danger[aria-label='Remove']"));

        // Option B: match by tooltip attribute (if aria-label is missing at runtime)
        //WebElement removeBtn = row.findElement(By.cssSelector("a.btn.btn-danger[data-bs-original-title='Remove']"));

        // Option C: match by icon inside the link
         WebElement removeBtn = row.findElement(By.xpath(".//a[contains(@class,'btn-danger')][.//i[contains(@class,'fa-circle-xmark')]]"));

        ui.click(removeBtn);
    }


    /** Get Sub-Total */
    public String getSubTotal() {
        return ui.text(subTotalValue);
    }

    /** Get Total */
    public String getTotal() {
        return ui.text(totalValue);
    }

    public OpenCartHomePage continueShopping() {
        ui.click(continueShoppingButton);
        return new OpenCartHomePage(ui);
    }
    
    public boolean isProductRemoved(String productName) {
        try {
            ui.invisibilityOfElementLocated(productName);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    

    public OpenCartCheckoutPage goToCheckout() {
        ui.click(checkoutButton);
        return new OpenCartCheckoutPage(ui);
    }


    
}