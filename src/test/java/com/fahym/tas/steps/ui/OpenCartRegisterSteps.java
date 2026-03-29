package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.data.factories.UserDataFactory;
import com.fahym.tas.data.models.UserData;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;
import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCartRegisterSteps {

    private final Config cfg = ConfigLoader.load();

    private OpenCartRegisterPage registerPage;
    private UserData generatedUser;
    

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
    
    @When("the user fills the registration form with a generated valid user")
    public void fillRegisterWithGeneratedValidUser() {
        generatedUser = UserDataFactory.validRegistrationUser();

        registerPage
                .setFirstName(generatedUser.firstName())
                .setLastName(generatedUser.lastName())
                .setEmail(generatedUser.email())
                .setPassword(generatedUser.password());
    }

    @When("the user agrees to the privacy policy")
    public void agreePrivacyPolicy() {
        registerPage.agreePrivacyPolicy();
    }

    @When("the user submits the registration form")
    public void submitRegistrationForm() {
        registerPage.submit();
    }
    
    @When("the user accept the registration alert")
    public void acceptRegistrationAlert() {
    	registerPage.acceptAlert();
    }

    @Then("no mandatory field validation errors should be displayed")
    public void assertNoMandatoryErrors() {
        // The goal is to verify the generated data is accepted as valid input.
        assertThat(registerPage.isFirstNameErrorVisible()).isFalse();
        assertThat(registerPage.isLastNameErrorVisible()).isFalse();
        assertThat(registerPage.isEmailErrorVisible()).isFalse();
        assertThat(registerPage.isPasswordErrorVisible()).isFalse();
    }
    
    
    
    @When("the user submits the registration form without filling any fields")
    public void submitEmptyRegisterForm() {
        registerPage.submit();
    }

    @Then("all mandatory field validation errors should be displayed")
    public void assertAllValidationErrors() {
        assertThat(registerPage.isFirstNameErrorVisible()).isTrue();
        assertThat(registerPage.isLastNameErrorVisible()).isTrue();
        assertThat(registerPage.isEmailErrorVisible()).isTrue();
        assertThat(registerPage.isPasswordErrorVisible()).isTrue();
    }
}