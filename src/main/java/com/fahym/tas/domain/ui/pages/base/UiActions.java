package com.fahym.tas.domain.ui.pages.base;

import java.time.Duration;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumActions;
import com.fahym.tas.core.driver.selenium.SeleniumWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class UiActions {

    private final WebDriver driver;
    private final SeleniumActions actions;
    private final SeleniumWaits waits;

    public UiActions(Duration timeout) {
        this.driver = DriverManager.getDriver();
        this.actions = new SeleniumActions(driver, timeout);
        this.waits = new SeleniumWaits(driver, timeout);
    }

    public void open(String url) {
        actions.open(url);
    }

    public void click(By locator) {
        actions.click(locator);
    }

    public void type(By locator, String text) {
        actions.type(locator, text);
    }

    public String text(By locator) {
        return actions.text(locator);
    }

    public boolean isVisible(By locator) {
        return actions.isVisible(locator);
    }

    public WebElement waitVisible(By locator) {
        return waits.untilVisible(locator);
    }

    public WebElement waitClickable(By locator) {
        return waits.untilClickable(locator);
    }

    public void waitDocumentReady() {
        waits.untilDocumentReady();
    }
}