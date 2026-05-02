package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;

import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;

import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ForgotPasswordPageSteps {

    private final Config cfg = ConfigLoader.load();

    private static final String REGISTERED_EMAIL = "testuser@example.com";
    private static final String UNREGISTERED_EMAIL = "unknownuser@test.com";
    private static final String INVALID_EMAIL = "invalid@email";

    private OpenCartForgottenPage forgottenPage;
    private OpenCartRegisterPage registerPage;

    @Given("the user opens the OpenCart forgotten password page")
    public void openForgottenPasswordPage() {
        UiActions ui = new UiActions(cfg.timeout());
        forgottenPage = new OpenCartForgottenPage(ui).open();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the OpenCart forgotten password page should be loaded")
    public void assertForgottenPasswordPageLoaded() {
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the forgotten password instruction text should be displayed")
    public void assertInstructionTextDisplayed() {
        assertThat(forgottenPage.hasInstructionText()).isTrue();
    }

    @Then("the email field should be displayed on forgotten password page")
    public void assertEmailFieldDisplayed() {
        assertThat(forgottenPage.isEmailFieldVisible()).isTrue();
    }

    @Then("the continue button should be displayed on forgotten password page")
    public void assertContinueButtonDisplayed() {
        assertThat(forgottenPage.isContinueButtonVisible()).isTrue();
    }

    @Then("the back button should be displayed on forgotten password page")
    public void assertBackButtonDisplayed() {
        assertThat(forgottenPage.isBackButtonVisible()).isTrue();
    }

    @When("the user enters a registered email on forgotten password page")
    public void enterRegisteredEmail() {
        forgottenPage.enterEmail(REGISTERED_EMAIL);
    }

    @When("the user enters an unregistered email on forgotten password page")
    public void enterUnregisteredEmail() {
        forgottenPage.enterEmail(UNREGISTERED_EMAIL);
    }

    @When("the user enters an invalid email on forgotten password page")
    public void enterInvalidEmail() {
        forgottenPage.enterEmail(INVALID_EMAIL);
    }

    @When("the user submits the forgotten password form")
    public void submitForgottenPasswordForm() {
        forgottenPage.submitContinue();
    }

    @When("the user submits the forgotten password form without email")
    public void submitForgottenPasswordWithoutEmail() {
        forgottenPage.submitContinue();
    }

    @Then("the forgotten password request should be accepted")
    public void assertForgottenPasswordRequestAccepted() {
        assertThat(forgottenPage.isRequestAccepted()).isTrue();
    }

    @Then("the email validation error should be displayed on forgotten password page")
    public void assertEmailValidationErrorDisplayed() {
        assertThat(forgottenPage.isEmailErrorVisible()).isTrue();
    }

    @When("the user clicks the back button on forgotten password page")
    public void clickBackButtonOnForgottenPasswordPage() {
        forgottenPage.goBackToLogin();
    }
    
    @Then("the forgotten password page breadcrumb should display {string}")
    public void assertForgottenPasswordBreadcrumbItem(String expectedText) {
        assertThat(forgottenPage.hasBreadcrumbItem(expectedText)).isTrue();
    }

    @When("the user clicks the side menu login link on forgotten password page")
    public void clickSideMenuLoginLinkOnForgottenPasswordPage() {
        forgottenPage.goToSideMenuLogin();
    }

    @When("the user clicks the side menu register link on forgotten password page")
    public void clickSideMenuRegisterLinkOnForgottenPasswordPage() {
        registerPage = forgottenPage.goToSideMenuRegister();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu forgotten password link on forgotten password page")
    public void clickSideMenuForgottenPasswordLinkOnForgottenPasswordPage() {
        forgottenPage = forgottenPage.goToSideMenuForgottenPassword();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the register account page should be loaded from forgotten password flow")
    public void assertRegisterPageLoadedFromForgottenPasswordFlow() {
        assertThat(registerPage.isLoaded()).isTrue();
    }

    
    @Then("the forgotten password page URL should use HTTPS")
    public void assertForgottenPasswordPageUsesHttps() {
        assertThat(forgottenPage.isHttps()).isTrue();
    }

    @When("the user enters malicious email input on forgotten password page")
    public void enterMaliciousEmailInputOnForgottenPasswordPage() {
        forgottenPage.enterEmail("<script>alert('x')</script>");
    }

    @When("the user enters a very long email on forgotten password page")
    public void enterVeryLongEmailOnForgottenPasswordPage() {
        forgottenPage.enterEmail("verylongemail" + "x".repeat(260) + "@test.com");
    }

    @When("the user submits the forgotten password form multiple times with unregistered email")
    public void submitForgottenPasswordFormMultipleTimesWithUnregisteredEmail() {
        for (int i = 0; i < 3; i++) {
            forgottenPage.enterEmail(UNREGISTERED_EMAIL);
            forgottenPage.submitContinue();
        }
    }

    @Then("the forgotten password page should remain stable")
    public void assertForgottenPasswordPageRemainsStable() {
        forgottenPage = new OpenCartForgottenPage(new UiActions(cfg.timeout()));
        assertThat(forgottenPage.isLoaded()).isTrue();
        assertThat(forgottenPage.isEmailFieldVisible()).isTrue();
        assertThat(forgottenPage.isContinueButtonVisible()).isTrue();
        assertThat(forgottenPage.isBackButtonVisible()).isTrue();
    }

    @Then("no JavaScript alert should be displayed on forgotten password page")
    public void assertNoJavaScriptAlertDisplayedOnForgottenPasswordPage() {
        assertThat(forgottenPage.hasBrowserAlert()).isFalse();
    }
    
    @When("the user sets the browser viewport to desktop size on forgotten password page")
    public void setDesktopViewportOnForgottenPasswordPage() {
        forgottenPage.setDesktopViewport();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to tablet size on forgotten password page")
    public void setTabletViewportOnForgottenPasswordPage() {
        forgottenPage.setTabletViewport();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to mobile size on forgotten password page")
    public void setMobileViewportOnForgottenPasswordPage() {
        forgottenPage.setMobileViewport();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the forgotten password form should remain usable")
    public void assertForgottenPasswordFormUsable() {
        assertThat(forgottenPage.isFormUsable()).isTrue();
    }

    @Then("the forgotten password page primary elements should be visible")
    public void assertForgottenPasswordPrimaryElementsVisible() {
        assertThat(forgottenPage.isEmailFieldVisible()).isTrue();
        assertThat(forgottenPage.isContinueButtonVisible()).isTrue();
        assertThat(forgottenPage.isBackButtonVisible()).isTrue();
    }
    
    
    @When("the user refreshes the forgotten password page")
    public void refreshForgottenPasswordPage() {
        forgottenPage.refresh();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the email field should be empty on forgotten password page")
    public void assertEmailFieldEmptyOnForgottenPasswordPage() {
        assertThat(forgottenPage.emailValue()).isEmpty();
    }

    @When("the user navigates back in the browser from login to forgotten password page")
    public void navigateBackInBrowserFromLoginToForgottenPasswordPage() {
        forgottenPage = new OpenCartForgottenPage(new UiActions(cfg.timeout()));
        forgottenPage.goBack();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user opens the OpenCart forgotten password page again")
    public void openForgottenPasswordPageAgain() {
        forgottenPage = new OpenCartForgottenPage(new UiActions(cfg.timeout())).open();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }
    
}