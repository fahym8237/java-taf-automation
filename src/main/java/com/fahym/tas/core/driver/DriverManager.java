package com.fahym.tas.core.driver;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.utils.Exceptions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverManager {
    private DriverManager() {}

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void createDriverIfNeeded(Config cfg) {
        if (DRIVER.get() != null) return;

        String browser = cfg.browser();
        boolean headless = cfg.headless();

        WebDriver driver;
        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new org.openqa.selenium.chrome.ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("-headless");
                driver = new org.openqa.selenium.firefox.FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new org.openqa.selenium.edge.EdgeDriver(options);
            }
            default -> throw Exceptions.illegalState("Unsupported browser: " + browser + " (use chrome|firefox|edge)");
        }

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver d = DRIVER.get();
        if (d == null) throw Exceptions.illegalState("WebDriver not initialized. Call createDriverIfNeeded() first.");
        return d;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        try {
            if (d != null) d.quit();
        } finally {
            DRIVER.remove();
        }
    }
}
