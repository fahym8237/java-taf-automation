package com.fahym.tas.infra.browser;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.infra.execution.ExecutionStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class LocalBrowserProvider implements BrowserProvider {

    @Override
    public WebDriver create(Config cfg, ExecutionStrategy strategy) {
        // strategy.target should be LOCAL here; we still keep method signature uniform
        String browser = cfg.browser();
        boolean headless = cfg.headless();

        return switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            case "edge" -> new EdgeDriver(edgeOptions(headless));
            default -> throw new IllegalStateException("Unsupported browser: " + browser + " (use chrome|firefox|edge)");
        };
    }

    private ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        return options;
    }

    private FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("-headless");
        return options;
    }

    private EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        return options;
    }
}
