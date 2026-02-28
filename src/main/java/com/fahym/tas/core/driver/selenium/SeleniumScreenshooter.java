package com.fahym.tas.core.driver.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class SeleniumScreenshooter {

    private SeleniumScreenshooter() {}

    public static byte[] capturePng(WebDriver driver) {
        if (!(driver instanceof TakesScreenshot ts)) {
            throw new IllegalStateException("Driver does not support screenshots: " + driver.getClass().getName());
        }
        return ts.getScreenshotAs(OutputType.BYTES);
    }
}
