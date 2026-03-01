package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;
import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCartRegisterSteps {

    private final Config cfg = ConfigLoader.load();

    private OpenCartRegisterPage registerPage;

    @Given("the user opens the OpenCart register page")
    public void openRegisterPage() {
        UiActions ui = new UiActions(cfg.timeout());
        registerPage = new OpenCartRegisterPage(ui).open();
        assertThat(registerPage.isLoaded()).isTrue();
    }
    
    @When("the user check the Privacy Policy")
    public void checkPrivacyPolicy() {
    	registerPage.agreePrivacyPolicy();
    }

    @And("the user submits the registration form without filling any fields")
    public void submitEmptyRegisterForm() {
        registerPage.submit();
        
    }
    

    @Then("all mandatory field validation errors should be displayed")
    public void assertAllValidationErrors() {

        assertThat(registerPage.isFirstNameErrorVisible())
                .isTrue();

        assertThat(registerPage.isLastNameErrorVisible())
                .isTrue();

        assertThat(registerPage.isEmailErrorVisible())
                .isTrue();

        assertThat(registerPage.isPasswordErrorVisible())
                .isTrue();
    }
}
