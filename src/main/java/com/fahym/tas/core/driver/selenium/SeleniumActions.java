package com.fahym.tas.core.driver.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class SeleniumActions {

    private final WebDriver driver;
    private final SeleniumWaits waits;

    public SeleniumActions(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.waits = new SeleniumWaits(driver, timeout);
    }

    public void open(String url) {
        driver.get(url);
        waits.untilDocumentReady();
    }

    public void click(By locator) {
        waits.untilClickable(locator).click();
    }

    public void type(By locator, String text) {
        WebElement el = waits.untilVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public String text(By locator) {
        return waits.untilVisible(locator).getText();
    }

    public boolean isVisible(By locator) {
        try {
            waits.untilVisible(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollIntoView(By locator) {
        WebElement el = waits.untilVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public void jsClick(By locator) {
        WebElement el = waits.untilVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}
