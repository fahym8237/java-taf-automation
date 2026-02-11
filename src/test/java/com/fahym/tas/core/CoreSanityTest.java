// Automation Core Layer Verification Test (JUnit 5)

package com.fahym.tas.core;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.stability.Waits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class CoreSanityTest {

    // OpenCart login page locator you provided
    private static final By returningCustomerHeader =
            By.xpath("//h2[normalize-space()='Returning Customer']");

    @AfterEach
    void cleanup() {
        DriverManager.quitDriver();
    }

    @Test
    void shouldOpenOpenCartLoginAndSeeReturningCustomerHeader() {
        Config cfg = ConfigLoader.load();

        DriverManager.createDriverIfNeeded(cfg);
        DriverManager.getDriver().get(cfg.baseUrl());

        Waits waits = new Waits(cfg);
        String headerText = waits.visible(returningCustomerHeader).getText();

        assertThat(headerText).isEqualTo("Returning Customer");
    }
}
