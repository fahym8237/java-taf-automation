package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.domain.ui.flows.OpenCartAuthFlow;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCartAuthSteps {

    private final Config cfg = ConfigLoader.load();

    private OpenCartAuthFlow flow;
    private OpenCartLoginPage loginPage;
    private OpenCartForgottenPage forgottenPage;

    @Given("the user opens the OpenCart login page")
    public void openLoginPage() {
        flow = new OpenCartAuthFlow(cfg.timeout());
        loginPage = flow.openLogin();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @When("the user navigates to the forgotten password page")
    public void navigateToForgottenPassword() {
        forgottenPage = loginPage.goToForgottenPassword();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user submits the forgotten password form without email")
    public void submitForgottenWithoutEmail() {
        forgottenPage.submitContinue();
    }

    @Then("the email validation error should be displayed")
    public void assertEmailErrorDisplayed() {
        assertThat(forgottenPage.isEmailErrorVisible()).isTrue();
        // assertThat(false).isTrue(); // force failure to test if the Failure Screenshot are generated in target/cucumber/cucumber.html
    }
}
