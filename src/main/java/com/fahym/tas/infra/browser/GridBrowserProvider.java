package com.fahym.tas.infra.browser;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.infra.execution.ExecutionStrategy;

import java.net.MalformedURLException;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class GridBrowserProvider implements BrowserProvider {

    @Override
    public WebDriver create(Config cfg, ExecutionStrategy strategy) {
        MutableCapabilities caps = new MutableCapabilities();
        // Minimal: let Selenium grid decide based on "browserName"
        caps.setCapability("browserName", cfg.browser());

        // Add headless flags via options later (Layer E expansion)
        try {
			return new RemoteWebDriver(strategy.remoteUrl().toURL(), caps);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // NOTE: needs try/catch in real code
		return null;
    }
}
