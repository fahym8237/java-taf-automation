package com.fahym.tas.hooks;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumScreenshooter;
import com.fahym.tas.steps.context.ScenarioContextProvider;
import io.cucumber.java.*;

public class CucumberHooks {

    private Config cfg;

    @Before(order = 10) //priority 10 (Lower numbers run first, higher numbers run later)
    public void beforeScenario(Scenario scenario) {
        ScenarioContextProvider.init(); //Initializes a scenario context provider.
        cfg = ConfigLoader.load(); //Loads configuration
        
        //If the scenario has the @ui tag, it spins up a Selenium driver
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.createDriverIfNeeded(cfg);
        }
    }

    @AfterStep(order = 90)
    public void afterStep(Scenario scenario) {
        // Attach screenshot only it to the Cucumber report when UI + failed step
        if (scenario.isFailed()
                && scenario.getSourceTagNames().contains("@ui")
                && DriverManager.hasDriver()) {

            byte[] png = SeleniumScreenshooter.capturePng(DriverManager.getDriver());
            scenario.attach(png, "image/png", "Failure Screenshot");
        }
    }

    @After(order = 100)
    public void afterScenario(Scenario scenario) {
    	//If the scenario is tagged @ui, quits the driver
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverManager.quitDriver();
        }
        ScenarioContextProvider.dispose();
    }
}
