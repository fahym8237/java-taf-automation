package com.fahym.tas.hooks;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.qualitygates.GateReporter;
import com.fahym.tas.governance.qualitygates.GateRunner;
import com.fahym.tas.observability.attachments.HtmlDumpService;
import com.fahym.tas.observability.attachments.ScreenshotService;
import com.fahym.tas.steps.context.ScenarioContextProvider;
import io.cucumber.java.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import com.fahym.tas.observability.api.ApiCallRecorder;
import io.qameta.allure.Allure;

public class CucumberHooks {

    private static final Logger log = LoggerFactory.getLogger(CucumberHooks.class);

    private Config cfg;

    @Before(order = 5)
	public void governanceBeforeScenario(Scenario scenario) {
	    // Convert Collection -> Set to match GateRunner API and ensure uniqueness
	    java.util.Set<String> tags = new java.util.HashSet<>(scenario.getSourceTagNames());
	    
	    GovernanceConfig gov = GovernanceConfig.fromSystem();
	    GateRunner runner = new GateRunner(); 
	
	    java.util.List<com.fahym.tas.governance.qualitygates.GateResult> results =
	            runner.evaluate(tags, gov);
	
	    // Record results for reporting
	    GateReporter.record(scenario.getName(), scenario.getId(), results);
	
	    // Fail fast if violations exist
	    if (!GateRunner.allPassed(results)) {
	        java.util.List<String> violations = GateRunner.allViolations(results);
	
	        throw new IllegalStateException(
	                "Governance gate failure for scenario: " + scenario.getName()
	                        + System.lineSeparator()
	                        + String.join(System.lineSeparator(), violations)
	        );
	    }
	}

    @Before(order = 10)
    public void beforeScenario(Scenario scenario) {
        ScenarioContextProvider.init();
        cfg = ConfigLoader.load();
        ApiCallRecorder.clear();
        boolean allureMetadataWritten = false;

        log.info("START Scenario: {} | Tags: {}", scenario.getName(), scenario.getSourceTagNames());

        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.createDriverIfNeeded(cfg);
        }
        
        if (!allureMetadataWritten) {
            synchronized (CucumberHooks.class) {
                if (!allureMetadataWritten) {
                    com.fahym.tas.observability.allure.AllureMetadataWriter.writeEnvironment(cfg);
                    com.fahym.tas.observability.allure.AllureMetadataWriter.writeExecutor();
                    allureMetadataWritten = true;
                }
            }
        }
        com.fahym.tas.observability.allure.AllureLabeler.applyLabels(scenario);
        com.fahym.tas.observability.allure.AllureMetadataWriter.writeEnvironment(cfg);
        com.fahym.tas.observability.allure.AllureMetadataWriter.writeExecutor();
        com.fahym.tas.observability.allure.AllureMetadataWriter.copyCategoriesIfPresent();
    }

    @AfterStep(order = 90)
	public void afterStep(Scenario scenario) {
	    if (!scenario.isFailed()) return;
	
	    String scenarioId = scenario.getId().replaceAll("[^a-zA-Z0-9._-]+", "_");
	
	    // --- API failure evidence ---
	    if (scenario.getSourceTagNames().contains("@api")
	            && com.fahym.tas.observability.api.ApiCallRecorder.hasLast()) {
	
	        var ex = com.fahym.tas.observability.api.ApiCallRecorder.last();
	
	        // Persist request/response on disk
	        var responseFile = com.fahym.tas.observability.api.ApiEvidenceWriter.writeLastExchange(scenarioId, ex);
	        log.info("Saved API evidence (last response): {}", responseFile);
	
	        // Attach readable summary to Cucumber report
	        String summary = com.fahym.tas.observability.api.ApiEvidenceWriter.toReadableSummary(ex);
	       // scenario.attach(summary.getBytes(java.nio.charset.StandardCharsets.UTF_8),"text/plain", "API Last Exchange");
	        
	        //Allure.addAttachment("API Last Exchange", "text/plain", summary, ".txt");
	        //Allure.addAttachment("API Last Request", "application/json", ex.requestBody(), ".json");
	       //Allure.addAttachment("API Last Response", "application/json", ex.responseBody(), ".json");
	        
	        io.qameta.allure.Allure.addAttachment("API Last Exchange", "text/plain", summary, ".txt");
	        io.qameta.allure.Allure.addAttachment("API Last Request", "application/json",
	                ex.requestBody() == null ? "" : ex.requestBody(), ".json");
	        io.qameta.allure.Allure.addAttachment("API Last Response", "application/json",
	                ex.responseBody() == null ? "" : ex.responseBody(), ".json");
	    }

	    // --- UI failure evidence ---
	    if (scenario.getSourceTagNames().contains("@ui") && DriverManager.hasDriver()) {
	        Path pngPath = ScreenshotService.captureToDisk(scenarioId, "failure");
	        log.info("Saved screenshot: {}", pngPath);
	
	        Path htmlPath = HtmlDumpService.dumpPageSource(scenarioId, "page");
	        log.info("Saved page source: {}", htmlPath);
	
	        byte[] pngBytes = ScreenshotService.captureBytes();
	       // scenario.attach(pngBytes, "image/png", "Failure Screenshot");
	        
	        //Allure.addAttachment("Failure Screenshot", "image/png", new java.io.ByteArrayInputStream(pngBytes), ".png");
	        
	        io.qameta.allure.Allure.addAttachment(
	                "Failure Screenshot",
	                "image/png",
	                new java.io.ByteArrayInputStream(pngBytes),
	                ".png"
	        );
	        
	        String html = DriverManager.getDriver().getPageSource();
	       // Allure.addAttachment("Page Source", "text/html", html, ".html");
	        io.qameta.allure.Allure.addAttachment("Page Source", "text/html", html, ".html");
	        
	        
	    }
}

    @After(order = 100)
    public void afterScenario(Scenario scenario) {
        log.info("END Scenario: {} | Status: {}", scenario.getName(), scenario.getStatus());

        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.quitDriver();
        }
        ScenarioContextProvider.dispose();
    }

    @AfterAll
    public static void afterAll() {
        try {
            var file = GateReporter.flushToDisk();
            LoggerFactory.getLogger(CucumberHooks.class).info("Governance gate report written: {}", file);
        } catch (Exception e) {
            LoggerFactory.getLogger(CucumberHooks.class).warn("Failed to write governance gate report", e);
        }
    }
}