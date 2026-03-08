package com.fahym.tas.hooks;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.observability.attachments.HtmlDumpService;
import com.fahym.tas.observability.attachments.ScreenshotService;
import com.fahym.tas.steps.context.ScenarioContextProvider;
import io.cucumber.java.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class CucumberHooks {

    private static final Logger log = LoggerFactory.getLogger(CucumberHooks.class);

    private Config cfg;

    @Before(order = 10)
    public void beforeScenario(Scenario scenario) {
        ScenarioContextProvider.init();
        cfg = ConfigLoader.load();

        log.info("START Scenario: {} | Tags: {}", scenario.getName(), scenario.getSourceTagNames());

        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.createDriverIfNeeded(cfg);
        }
    }

    @AfterStep(order = 90)
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) return;
        if (!scenario.getSourceTagNames().contains("@ui")) return;
        if (!DriverManager.hasDriver()) return;

        // Use deterministic scenario id for filenames
        String scenarioId = scenario.getId().replaceAll("[^a-zA-Z0-9._-]+", "_");

        // 1) Persist screenshot on disk
        Path pngPath = ScreenshotService.captureToDisk(scenarioId, "failure");
        log.info("Saved screenshot: {}", pngPath);

        // 2) Persist page source on disk
        Path htmlPath = HtmlDumpService.dumpPageSource(scenarioId, "page");
        log.info("Saved page source: {}", htmlPath);

        // 3) Attach screenshot to Cucumber report as before
        byte[] pngBytes = ScreenshotService.captureBytes();
        scenario.attach(pngBytes, "image/png", "Failure Screenshot");
    }

    @After(order = 100)
    public void afterScenario(Scenario scenario) {
        log.info("END Scenario: {} | Status: {}", scenario.getName(), scenario.getStatus());

        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.quitDriver();
        }
        ScenarioContextProvider.dispose();
    }
}
