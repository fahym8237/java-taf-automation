package com.fahym.tas.core.driver.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * SeleniumWaits
 *
 * Centralized explicit wait utility used by SeleniumActions and UI interactions.
 * This class encapsulates common synchronization patterns required for
 * reliable UI automation in dynamic web applications.
 *
 * All waits rely on WebDriverWait and ExpectedConditions to avoid flaky tests.
 */
public final class SeleniumWaits {

    private final WebDriver driver;
    private final Duration timeout;

    public SeleniumWaits(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    /**
     * Wait until an element is visible in the DOM and displayed on the page.
     * Used when the test needs to interact with an element that must be visible.
     */
    public WebElement untilVisible(By locator) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until an element becomes clickable.
     * This ensures the element is both visible and enabled before performing a click.
     */
    public WebElement untilClickable(By locator) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait until an element becomes invisible or disappears from the page.
     * Useful for waiting for loaders, spinners, or modal dialogs to close.
     */
    public boolean untilInvisible(By locator) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait until an element is present in the DOM.
     * The element may not yet be visible on the page.
     * Useful when waiting for dynamically injected elements.
     */
    public WebElement untilPresent(By locator) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait until a specific text appears inside an element.
     * Commonly used for status messages or UI updates.
     */
    public boolean untilTextPresent(By locator, String text) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait until an element attribute contains a specific value.
     * Useful when UI state changes dynamically via JavaScript.
     */
    public boolean untilAttributeContains(By locator, String attribute, String value) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

   

    /**
     * Wait until a previously located element becomes stale.
     * This occurs when the DOM refreshes or the element is replaced.
     */
    public boolean untilStale(WebElement element) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.stalenessOf(element));
    }

    /**
     * Wait until a frame becomes available and switch the driver context to it.
     * Required when interacting with elements inside iframes.
     */
    public void untilFrameAndSwitch(By locator) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    /**
     * Wait until a browser alert dialog appears.
     * Used when applications trigger native JavaScript alerts.
     */
    public Alert untilAlertPresent() {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Wait until the current page URL contains the given fragment.
     * Useful for verifying navigation events.
     */
    public boolean untilUrlContains(String fragment) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.urlContains(fragment));
    }

    /**
     * Wait until the page title contains a specific text.
     * Helpful for confirming page transitions.
     */
    public boolean untilTitleContains(String title) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.titleContains(title));
    }

    /**
     * Wait until the document is fully loaded.
     * Checks the browser document.readyState property.
     */
    public void untilDocumentReady() {
        new WebDriverWait(driver, timeout).until(d -> {
            Object state = ((JavascriptExecutor) d).executeScript("return document.readyState");
            return "complete".equals(state);
        });
    }

    /**
     * Wait until a custom JavaScript condition returns true.
     * Useful for waiting for AJAX completion or framework-specific states.
     */
    public void untilJsCondition(String script) {
        new WebDriverWait(driver, timeout)
                .until(d -> Boolean.TRUE.equals(
                        ((JavascriptExecutor) d).executeScript(script)
                ));
    }

    /**
     * Wait until all elements matching the locator become visible.
     * Useful for lists or table rows.
     */
    public List<WebElement> untilAllVisible(By locator) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
}