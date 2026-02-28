package com.fahym.tas.core;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumActions;
import com.fahym.tas.core.driver.selenium.SeleniumWaits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class UiInteractionSmokeTest {

    // Login page
    private static final By forgottenPasswordLink = By.linkText("Forgotten Password");

    // Forgotten page
    private static final By forgottenPasswordHeader =
            By.xpath("//h1[normalize-space()='Forgot Your Password?']");

    @AfterEach
    void cleanup() {
        DriverManager.quitDriver();
    }

    @Test
    void shouldNavigateToForgottenPasswordPageUsingSeleniumWrappers() {
        Config cfg = ConfigLoader.load();

        DriverManager.createDriverIfNeeded(cfg);

        SeleniumActions actions = new SeleniumActions(DriverManager.getDriver(), cfg.timeout());
        actions.open(cfg.baseUrl());
        actions.click(forgottenPasswordLink);

        SeleniumWaits waits = new SeleniumWaits(DriverManager.getDriver(), cfg.timeout());
        String header = waits.untilVisible(forgottenPasswordHeader).getText();

        assertThat(header).isEqualTo("Forgot Your Password?");
    }
}
