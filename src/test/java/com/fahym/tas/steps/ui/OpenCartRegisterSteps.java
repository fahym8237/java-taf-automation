package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.data.factories.UserDataFactory;
import com.fahym.tas.data.models.UserData;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;
import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCartRegisterSteps {

    private final Config cfg = ConfigLoader.load();

    private static final String INVALID_EMAIL = "invalid@email";

    private OpenCartRegisterPage registerPage;
    private UserData generatedUser;
    private OpenCartLoginPage loginPage;
    private OpenCartForgottenPage forgottenPage;

    @Given("the user opens the OpenCart register page")
    public void openRegisterPage() {
        UiActions ui = new UiActions(cfg.timeout());
        registerPage = new OpenCartRegisterPage(ui).open();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @Then("the OpenCart register page should be loaded")
    public void assertRegisterPageLoaded() {
        registerPage = new OpenCartRegisterPage(new UiActions(cfg.timeout()));
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @Then("the first name field should be displayed on register page")
    public void assertFirstNameFieldDisplayed() {
        assertThat(registerPage.isFirstNameFieldVisible()).isTrue();
    }

    @Then("the last name field should be displayed on register page")
    public void assertLastNameFieldDisplayed() {
        assertThat(registerPage.isLastNameFieldVisible()).isTrue();
    }

    @Then("the email field should be displayed on register page")
    public void assertEmailFieldDisplayed() {
        assertThat(registerPage.isEmailFieldVisible()).isTrue();
    }

    @Then("the password field should be displayed on register page")
    public void assertPasswordFieldDisplayed() {
        assertThat(registerPage.isPasswordFieldVisible()).isTrue();
    }

    @Then("the continue button should be displayed on register page")
    public void assertContinueButtonDisplayed() {
        assertThat(registerPage.isContinueButtonVisible()).isTrue();
    }

    @Then("the privacy policy link should be displayed on register page")
    public void assertPrivacyPolicyLinkDisplayed() {
        assertThat(registerPage.isPrivacyPolicyLinkVisible()).isTrue();
    }

    @Then("the login link should be displayed on register page")
    public void assertLoginLinkDisplayed() {
        assertThat(registerPage.isLoginLinkVisible()).isTrue();
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

    @When("the user check the Privacy Policy")
    public void checkPrivacyPolicy() {
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

    @When("the user submits the registration form without filling any fields")
    public void submitEmptyRegisterForm() {
        registerPage.submit();
    }

    @When("the user enters an invalid email on register page")
    public void enterInvalidEmailOnRegisterPage() {
        registerPage.clearEmail();
        registerPage.setEmail(INVALID_EMAIL);
    }

    @Then("the success message {string} should be visible")
    public void assertSuccessMessage(String expectedMessage) {
        assertThat(registerPage.successMessage()).isEqualTo(expectedMessage);
    }

    @Then("no mandatory field validation errors should be displayed")
    public void assertNoMandatoryErrors() {
        assertThat(registerPage.isFirstNameErrorVisible()).isFalse();
        assertThat(registerPage.isLastNameErrorVisible()).isFalse();
        assertThat(registerPage.isEmailErrorVisible()).isFalse();
        assertThat(registerPage.isPasswordErrorVisible()).isFalse();
    }

    @Then("all mandatory field validation errors should be displayed")
    public void assertAllValidationErrors() {
        assertThat(registerPage.isFirstNameErrorVisible()).isTrue();
        assertThat(registerPage.isLastNameErrorVisible()).isTrue();
        assertThat(registerPage.isEmailErrorVisible()).isTrue();
        assertThat(registerPage.isPasswordErrorVisible()).isTrue();
    }

    @Then("the email validation error should be displayed on register page")
    public void assertEmailValidationErrorDisplayed() {
        assertThat(registerPage.isEmailErrorVisible()).isTrue();
    }

    @Then("a privacy policy warning should be displayed")
    public void assertPrivacyPolicyWarningDisplayed() {
        assertThat(registerPage.isPrivacyPolicyWarningVisible()).isTrue();
    }

    @Then("the password field should mask the entered value on register page")
    public void assertPasswordFieldMasked() {
        assertThat(registerPage.isPasswordMasked()).isTrue();
    }
    
    @When("the user clicks the login link on register page")
    public void clickLoginLinkOnRegisterPage() {
        loginPage = registerPage.goToLoginPage();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @Then("the OpenCart login page should be loaded from register flow")
    public void assertLoginPageLoadedFromRegisterFlow() {
        loginPage = new OpenCartLoginPage(new UiActions(cfg.timeout()));
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @Then("the register page breadcrumb should display {string}")
    public void assertRegisterBreadcrumbItem(String expectedText) {
        assertThat(registerPage.hasBreadcrumbItem(expectedText)).isTrue();
    }

    @When("the user clicks the side menu login link on register page")
    public void clickSideMenuLoginLinkOnRegisterPage() {
        loginPage = registerPage.goToSideMenuLogin();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu register link on register page")
    public void clickSideMenuRegisterLinkOnRegisterPage() {
        registerPage = registerPage.goToSideMenuRegister();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu forgotten password link on register page")
    public void clickSideMenuForgottenPasswordLinkOnRegisterPage() {
        forgottenPage = registerPage.goToSideMenuForgottenPassword();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the OpenCart forgotten password page should be loaded from register flow")
    public void assertForgottenPasswordPageLoadedFromRegisterFlow() {
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user clicks the privacy policy link on register page")
    public void clickPrivacyPolicyLinkOnRegisterPage() {
        registerPage.openPrivacyPolicy();
    }

    @Then("the privacy policy should be opened from register page")
    public void assertPrivacyPolicyOpenedFromRegisterPage() {
        assertThat(registerPage.isPrivacyPolicyOpened()).isTrue();
    }
    
    
    @Then("the register page URL should use HTTPS")
    public void assertRegisterPageUsesHttps() {
        assertThat(registerPage.isHttps()).isTrue();
    }

    @When("the user enters malicious first name input on register page")
    public void enterMaliciousFirstNameOnRegisterPage() {
        registerPage.clearFirstName();
        registerPage.setFirstName("<script>alert('x')</script>");
    }

    @When("the user enters malicious last name input on register page")
    public void enterMaliciousLastNameOnRegisterPage() {
        registerPage.clearLastName();
        registerPage.setLastName("' OR 1=1 --");
    }

    @When("the user enters malicious email input on register page")
    public void enterMaliciousEmailOnRegisterPage() {
        registerPage.clearEmail();
        registerPage.setEmail("<script>@test.com");
    }

    @When("the user enters malicious password input on register page")
    public void enterMaliciousPasswordOnRegisterPage() {
        registerPage.clearPassword();
        registerPage.setPassword("' OR 1=1 --");
    }

    @When("the user enters a very long first name on register page")
    public void enterVeryLongFirstNameOnRegisterPage() {
        registerPage.clearFirstName();
        registerPage.setFirstName("A".repeat(300));
    }

    @When("the user enters a very long last name on register page")
    public void enterVeryLongLastNameOnRegisterPage() {
        registerPage.clearLastName();
        registerPage.setLastName("B".repeat(300));
    }

    @When("the user enters a very long email on register page")
    public void enterVeryLongEmailOnRegisterPage() {
        registerPage.clearEmail();
        registerPage.setEmail("verylongemail" + "x".repeat(260) + "@test.com");
    }

    @When("the user enters a very long password on register page")
    public void enterVeryLongPasswordOnRegisterPage() {
        registerPage.clearPassword();
        registerPage.setPassword("P".repeat(300));
    }

    @Then("the register page should remain stable")
    public void assertRegisterPageRemainsStable() {
        registerPage = new OpenCartRegisterPage(new UiActions(cfg.timeout()));
        assertThat(registerPage.isLoaded()).isTrue();
        assertThat(registerPage.isFirstNameFieldVisible()).isTrue();
        assertThat(registerPage.isLastNameFieldVisible()).isTrue();
        assertThat(registerPage.isEmailFieldVisible()).isTrue();
        assertThat(registerPage.isPasswordFieldVisible()).isTrue();
        assertThat(registerPage.isContinueButtonVisible()).isTrue();
    }
    
    @When("the user sets the browser viewport to desktop size on register page")
    public void setDesktopViewportOnRegisterPage() {
        registerPage.setDesktopViewport();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to tablet size on register page")
    public void setTabletViewportOnRegisterPage() {
        registerPage.setTabletViewport();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to mobile size on register page")
    public void setMobileViewportOnRegisterPage() {
        registerPage.setMobileViewport();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @Then("the register form should remain usable")
    public void assertRegisterFormUsable() {
        assertThat(registerPage.isFormUsable()).isTrue();
    }

    @Then("the register page primary elements should be visible")
    public void assertRegisterPrimaryElementsVisible() {
        assertThat(registerPage.isFirstNameFieldVisible()).isTrue();
        assertThat(registerPage.isLastNameFieldVisible()).isTrue();
        assertThat(registerPage.isEmailFieldVisible()).isTrue();
        assertThat(registerPage.isPasswordFieldVisible()).isTrue();
        assertThat(registerPage.isContinueButtonVisible()).isTrue();
    }
    
    @When("the user refreshes the register page")
    public void refreshRegisterPage() {
        registerPage.refresh();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @Then("the first name field should be empty on register page")
    public void assertFirstNameFieldEmptyOnRegisterPage() {
        assertThat(registerPage.firstNameValue()).isEmpty();
    }

    @Then("the last name field should be empty on register page")
    public void assertLastNameFieldEmptyOnRegisterPage() {
        assertThat(registerPage.lastNameValue()).isEmpty();
    }

    @Then("the email field should be empty on register page")
    public void assertEmailFieldEmptyOnRegisterPage() {
        assertThat(registerPage.emailValue()).isEmpty();
    }

    @Then("the password field should be empty on register page")
    public void assertPasswordFieldEmptyOnRegisterPage() {
        assertThat(registerPage.passwordValue()).isEmpty();
    }

    @When("the user navigates back in the browser from login to register page")
    public void navigateBackInBrowserFromLoginToRegisterPage() {
        registerPage = new OpenCartRegisterPage(new UiActions(cfg.timeout()));
        registerPage.goBack();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user opens the OpenCart register page again")
    public void openRegisterPageAgain() {
        registerPage = new OpenCartRegisterPage(new UiActions(cfg.timeout())).open();
        assertThat(registerPage.isLoaded()).isTrue();
    }
}