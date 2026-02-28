package com.fahym.tas.domain;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.domain.ui.assertions.OpenCartAuthAssertions;
import com.fahym.tas.domain.ui.flows.OpenCartAuthFlow;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OpenCartDomainSmokeTest {

    @AfterEach
    void cleanup() {
        DriverManager.quitDriver();
    }

    @Test
    void shouldNavigateLoginToForgottenAndValidatePagesUsingDomainOnly() {
        Config cfg = ConfigLoader.load();
        DriverManager.createDriverIfNeeded(cfg);

        OpenCartAuthFlow flow = new OpenCartAuthFlow(cfg.timeout());
        OpenCartAuthAssertions asserts = new OpenCartAuthAssertions();

        OpenCartLoginPage login = flow.openLogin();
        asserts.assertLoginPageLoaded(login);

        OpenCartForgottenPage forgotten = flow.openForgottenViaLogin();
        asserts.assertForgottenPasswordPageLoaded(forgotten);

        // trigger validation: submit without email
        forgotten.submitContinue();
        asserts.assertEmailValidationErrorVisible(forgotten);
    }
}