package com.fahym.tas.infra.browser;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.infra.execution.ExecutionStrategy;
import org.openqa.selenium.WebDriver;

public interface BrowserProvider {
    WebDriver create(Config cfg, ExecutionStrategy strategy);
}
