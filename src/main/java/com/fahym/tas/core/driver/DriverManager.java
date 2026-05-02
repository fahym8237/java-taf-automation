package com.fahym.tas.core.driver;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.utils.Exceptions;
import com.fahym.tas.infra.browser.BrowserProvider;
import com.fahym.tas.infra.browser.LocalBrowserProvider;
import com.fahym.tas.infra.execution.ExecutionResolver;
import com.fahym.tas.infra.execution.ExecutionStrategy;
import com.fahym.tas.infra.execution.ExecutionTarget;
import org.openqa.selenium.WebDriver;

public final class DriverManager {
    private DriverManager() {}

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void createDriverIfNeeded(Config cfg) {
        if (DRIVER.get() != null) return;

        ExecutionStrategy strategy = new ExecutionResolver().resolve(cfg);
        BrowserProvider provider = providerFor(strategy.target());

        WebDriver driver = provider.create(cfg, strategy);
        DRIVER.set(driver);
    }

    private static BrowserProvider providerFor(ExecutionTarget target) {
	    return switch (target) {
	        case LOCAL -> new LocalBrowserProvider();
	        case GRID -> new com.fahym.tas.infra.browser.GridBrowserProvider();
	        case CLOUD -> throw Exceptions.illegalState(
	                "Cloud execution not implemented yet.");
	    };
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
