package com.fahym.tas.core.driver.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * SeleniumActions
 *
 * High-level UI interaction utility built on top of Selenium WebDriver.
 * This class centralizes common browser interactions and ensures
 * stable operations by relying on SeleniumWaits before interacting
 * with elements.
 *
 * It is intended to be used by higher layers such as Page Objects
 * or Application Interaction components in the TAS framework.
 */
public final class SeleniumActions {

    private final WebDriver driver;
    private final SeleniumWaits waits;

    public SeleniumActions(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.waits = new SeleniumWaits(driver, timeout);
    }

    /**
     * Opens a URL in the browser and waits until the page is fully loaded.
     */
    public void open(String url) {
        driver.get(url);
        waits.untilDocumentReady();
    }

    /**
     * Clicks an element after waiting for it to become clickable.
     */
    public void click(By locator) {
        waits.untilClickable(locator).click();
    }

    /**
     * Performs a JavaScript click on an element.
     * Useful when normal Selenium click fails due to overlays or complex UI layers.
     */
    public void jsClick(By locator) {
        WebElement el = waits.untilVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    /**
     * Types text into an input field.
     * The field is cleared before typing.
     */
    public void type(By locator, String text) {
        WebElement el = waits.untilVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    /**
     * Clears the content of an input field.
     */
    public void clear(By locator) {
        waits.untilVisible(locator).clear();
    }

    /**
     * Returns the visible text of an element.
     */
    public String text(By locator) {
        return waits.untilVisible(locator).getText();
    }

    /**
     * Returns the value of an attribute from an element.
     * Useful for validating UI states such as "disabled", "value", etc.
     */
    public String attribute(By locator, String attribute) {
        return waits.untilVisible(locator).getAttribute(attribute);
    }

    /**
     * Checks whether an element is visible on the page.
     * Returns false if the element is not visible within the timeout.
     */
    public boolean isVisible(By locator) {
        try {
            waits.untilVisible(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether an element exists in the DOM.
     * Unlike isVisible(), this does not require the element to be displayed.
     */
    public boolean exists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    /**
     * Returns a list of elements matching the locator.
     * Useful for working with lists, tables, and collections.
     */
    public List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Scrolls the page until the element is brought into view.
     * The element is positioned near the center of the viewport.
     */
    public void scrollIntoView(By locator) {
        WebElement el = waits.untilVisible(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    /**
     * Submits a form element.
     * Typically used for HTML forms.
     */
    public void submit(By locator) {
        waits.untilVisible(locator).submit();
    }

    /**
     * Selects an option in a dropdown element using visible text.
     */
    public void selectByText(By locator, String text) {
        Select select = new Select(waits.untilVisible(locator));
        select.selectByVisibleText(text);
    }

    /**
     * Selects an option in a dropdown using the option value attribute.
     */
    public void selectByValue(By locator, String value) {
        Select select = new Select(waits.untilVisible(locator));
        select.selectByValue(value);
    }

    /**
     * Selects an option in a dropdown using its index.
     */
    public void selectByIndex(By locator, int index) {
        Select select = new Select(waits.untilVisible(locator));
        select.selectByIndex(index);
    }

    /**
     * Performs a mouse hover action over an element.
     * Useful for menus and tooltips.
     */
    public void hover(By locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waits.untilVisible(locator)).perform();
    }

    /**
     * Performs a double-click on an element.
     */
    public void doubleClick(By locator) {
        Actions actions = new Actions(driver);
        actions.doubleClick(waits.untilClickable(locator)).perform();
    }

    /**
     * Performs a right-click (context click) on an element.
     */
    public void rightClick(By locator) {
        Actions actions = new Actions(driver);
        actions.contextClick(waits.untilVisible(locator)).perform();
    }

    /**
     * Sends keyboard keys to an element.
     * Example usage: ENTER, TAB, ESC.
     */
    public void sendKeys(By locator, Keys key) {
        waits.untilVisible(locator).sendKeys(key);
    }

    /**
     * Returns the current browser URL.
     * Useful for verifying navigation after actions like login.
     */
    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Returns the current page title.
     */
    public String title() {
        return driver.getTitle();
    }

    /**
     * Switches the driver context to an iframe.
     */
    public void switchToFrame(By locator) {
        waits.untilFrameAndSwitch(locator);
    }

    /**
     * Switches the driver context back to the main document.
     */
    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    /**
     * Accepts a browser alert dialog.
     */
    public void acceptAlert() {
        Alert alert = waits.untilAlertPresent();
        alert.accept();
    }

    /**
     * Dismisses a browser alert dialog.
     */
    public void dismissAlert() {
        Alert alert = waits.untilAlertPresent();
        alert.dismiss();
    }
}