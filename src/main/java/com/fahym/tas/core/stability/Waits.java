package com.fahym.tas.core.stability;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class Waits {
    private final SeleniumWaits waits;

    public Waits(Config cfg) {
        this.waits = new SeleniumWaits(DriverManager.getDriver(), cfg.timeout());
    }

    public WebElement visible(By locator) {
        return waits.untilVisible(locator);
    }

    public WebElement clickable(By locator) {
        return waits.untilClickable(locator);
    }
}
