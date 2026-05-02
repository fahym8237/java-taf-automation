package com.fahym.tas.infra.browser;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.infra.execution.ExecutionStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public final class GridBrowserProvider implements BrowserProvider {

    @Override
    public WebDriver create(Config cfg, ExecutionStrategy strategy) {
        String browser = cfg.browser();
        boolean headless = cfg.headless();
        URI remoteUrl = strategy.remoteUrl();

        return switch (browser) {
            case "chrome" -> createRemoteChrome(remoteUrl, headless);
            default -> throw new IllegalStateException(
                    "GridBrowserProvider currently supports chrome only for this TAS update. Browser=" + browser
            );
        };
    }

    private WebDriver createRemoteChrome(URI remoteUrl, boolean headless) {
	    ChromeOptions options = new ChromeOptions();
	
	    if (headless) {
	        options.addArguments("--headless=new");
	    }
	
	    options.addArguments("--no-sandbox");
	    options.addArguments("--disable-dev-shm-usage");
	    options.addArguments("--window-size=1920,1080");
	
	    try {
	        return new RemoteWebDriver(remoteUrl.toURL(), options);
	    } catch (Exception e) {
	        throw new IllegalStateException("Invalid remote URL: " + remoteUrl, e);
	    }
}
}