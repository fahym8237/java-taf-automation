package com.fahym.tas.domain.ui.pages.base;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumActions;
import com.fahym.tas.core.driver.selenium.SeleniumWaits;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;



/**
 * UiActions
 *
 * Domain-facing UI action wrapper used by Page Objects and other domain-level
 * UI components. This class delegates concrete browser interactions to
 * SeleniumActions and explicit synchronization to SeleniumWaits, so higher
 * layers do not depend directly on Selenium APIs.
 *
 * It acts as a clean bridge between the Domain Abstraction Layer and the
 * Application Interaction Layer:
 * - Page Objects call UiActions to perform user-oriented operations.
 * - UiActions forwards interaction requests to SeleniumActions.
 * - UiActions uses SeleniumWaits when direct waiting behavior is needed.
 *
 * This separation improves maintainability by keeping Selenium-specific
 * details centralized in the core interaction classes.
 */
public final class UiActions {

    private final WebDriver driver;
    private final SeleniumActions actions;
    private final SeleniumWaits waits;
    private WebDriverWait wait;

    public UiActions(Duration timeout) {
        this.driver = DriverManager.getDriver();
        this.actions = new SeleniumActions(driver, timeout);
        this.waits = new SeleniumWaits(driver, timeout);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
    }

    /**
     * Opens a page URL and waits until the document is fully loaded.
     */
    public void open(String url) {
        actions.open(url);
    }

    /**
     * Clicks an element after waiting for it to become clickable.
     */
    public void click(By locator) {
        actions.click(locator);
    }
    
    public void click(WebElement el) {
        actions.click(el);
    }

    /**
     * Clicks an element using JavaScript.
     * Useful for UI cases where standard click is blocked by overlays or styling layers.
     */
    public void jsClick(By locator) {
        actions.jsClick(locator);
    }

    
    /**
     * Clicks an element using JavaScript.
     * Useful for UI cases where standard click is blocked by overlays or styling layers.
     */
    public void jsClick(WebElement el) {
        actions.jsClick(el);
    }
    /**
     * Types text into an input field after clearing its current value.
     */
    public void type(By locator, String text) {
        actions.type(locator, text);
    }
    
    public void type(WebElement el, String text) {
        actions.type(el, text);
    }

    /**
     * Clears the value of an input field.
     */
    public void clear(By locator) {
        actions.clear(locator);
    }
    
    public void clear(WebElement el) {
        actions.clear(el);
    }

    /**
     * Returns the visible text of an element.
     */
    public String text(By locator) {
        return actions.text(locator);
    }

    /**
     * Returns the value of a specific attribute from an element.
     */
    public String attribute(By locator, String attribute) {
        return actions.attribute(locator, attribute);
    }

    /**
     * Returns true if the element becomes visible within the configured timeout.
     */
    public boolean isVisible(By locator) {
        return actions.isVisible(locator);
    }

    /**
     * Returns true if the element exists in the DOM, even if it is not visible.
     */
    public boolean exists(By locator) {
        return actions.exists(locator);
    }

    /**
     * Returns all elements matching the locator.
     * Useful for lists, tables, repeated cards, or collections of controls.
     */
    public List<WebElement> elements(By locator) {
        return actions.elements(locator);
    }

    

    /**
     * Submits a form element.
     */
    public void submit(By locator) {
        actions.submit(locator);
    }

    /**
     * Selects a dropdown option by visible text.
     */
    public void selectByText(By locator, String text) {
        actions.selectByText(locator, text);
    }

    /**
     * Selects a dropdown option by value attribute.
     */
    public void selectByValue(By locator, String value) {
        actions.selectByValue(locator, value);
    }

    /**
     * Selects a dropdown option by index.
     */
    public void selectByIndex(By locator, int index) {
    	actions.scrollIntoView(locator);
        actions.selectByIndex(locator, index);
    }

    /**
     * Moves the mouse over an element.
     * Commonly used for menus, tooltips, and hover-triggered content.
     */
    public void hover(By locator) {
        actions.hover(locator);
    }

    /**
     * Performs a double-click on an element.
     */
    public void doubleClick(By locator) {
        actions.doubleClick(locator);
    }

    /**
     * Performs a right-click on an element.
     */
    public void rightClick(By locator) {
        actions.rightClick(locator);
    }

    /**
     * Sends a keyboard key to an element.
     * Typical use cases include ENTER, TAB, and ESCAPE.
     */
    public void sendKey(By locator, Keys key) {
        actions.sendKeys(locator, key);
    }

    /**
     * Returns the current browser URL.
     */
    public String currentUrl() {
        return actions.currentUrl();
    }

    /**
     * Returns the current page title.
     */
    public String title() {
        return actions.title();
    }

    /**
     * Switches the driver context to an iframe identified by the locator.
     */
    public void switchToFrame(By locator) {
        actions.switchToFrame(locator);
    }

    /**
     * Switches the driver context back to the main document.
     */
    public void switchToDefault() {
        actions.switchToDefault();
    }

    /**
     * Accepts the currently displayed browser alert.
     */
    public void acceptAlert() {
        actions.acceptAlert();
    }

    /**
     * Dismisses the currently displayed browser alert.
     */
    public void dismissAlert() {
        actions.dismissAlert();
    }

    /**
     * Waits until the target element becomes visible and returns it.
     */
    public WebElement waitVisible(By locator) {
        return waits.untilVisible(locator);
    }

    /**
     * Waits until the target element becomes clickable and returns it.
     */
    public WebElement waitClickable(By locator) {
        return waits.untilClickable(locator);
    }
    
    /**
     * Waits until the target element becomes clickable and returns it.
     */
    public WebElement waitClickable(WebElement el) {
        return waits.untilClickable(el);
    }

    /**
     * Waits until the current page document is fully loaded.
     */
    public void waitDocumentReady() {
        waits.untilDocumentReady();
    }
    
 // Wait until radio button is clickable, then click
    public void clickRadio(By locator) {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            radio.click(); // normal click
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Fallback 1: scroll into view and retry
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
            try {
                radio.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException ex) {
                // Fallback 2: force JS click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
            }
        }
    }

    
    public boolean isPresent(By locator) {
        return !DriverManager.getDriver().findElements(locator).isEmpty();
    }
    
    public boolean isVisibleNow(By locator) {
        try {
            return isPresent(locator) && DriverManager.getDriver().findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToBottom() {
        actions.scrollToBottom();
    }
    
    public void scrollIntoViewAndClick(WebElement el) {
    	actions.scrollIntoViewAndClick(el);
    }

    public void scrollIntoView(By locator) {
        actions.scrollIntoView(locator);
    }
     
    public void setViewportSize(int width, int height) {
        DriverManager.getDriver().manage().window().setSize(new Dimension(width, height));
        waitDocumentReady();
    }
    
    public void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'nearest', inline:'nearest'});", el);
    }

    
    /**
     * Returns true if the element passes HTML5 validation (no native alert).
     * Returns false if the browser-native validation alert would be shown.
     */
    public boolean isValid(By locator) {
        WebElement element = driver.findElement(locator);
        return (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();", element);
    }

    /**
     * Returns the browser-native validation message text for the element.
     * If the element is valid, this will usually be an empty string.
     */
    public String validationMessage(By locator) {
        WebElement element = driver.findElement(locator);
        return (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", element);
    }
    
    
    public boolean hasBrowserAlert() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void invisibilityOfElementLocated(String productName) throws TimeoutException {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@id='shopping-cart']//tbody/tr[td[contains(.,'" + productName + "')]]")
            ));
    }
    
    public void WaitUntilRemoveLinkDisappears(WebElement removeLink) {
    	 new WebDriverWait(driver, Duration.ofSeconds(5))   // <-- timeout here
                .until(ExpectedConditions.invisibilityOf(removeLink));
    }

 // Strong reusable method: select region by visible text
    public void selectRegion(By locator, String regionName) {
        actions.scrollIntoView(locator);

        // Wait until dropdown is enabled
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // Wait until the desired option appears
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, regionName));

        Select select = new Select(dropdown);
        select.selectByVisibleText(regionName);
    }

    
   



}