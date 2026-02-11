package com.fahym.tas.core.stability;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Waits {
    private final WebDriverWait wait;

    public Waits(Config cfg) {
        this.wait = new WebDriverWait(DriverManager.getDriver(), cfg.timeout());
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
