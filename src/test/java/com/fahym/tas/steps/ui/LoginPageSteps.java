package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.utils.Credentials;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;



import com.fahym.tas.domain.ui.pages.opencart.account.OpenCartMyAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.edit.OpenCartEditAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.password.OpenCartChangePasswordPage;

import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;



public class LoginPageSteps {

    private final Config cfg = ConfigLoader.load();

    private OpenCartLoginPage loginPage;
    private OpenCartForgottenPage forgottenPage;
    private OpenCartRegisterPage registerPage;
    private static Credentials credentials = Credentials.getInstance();

    
    private OpenCartMyAccountPage myAccountPage;
    
    
    // ========================
    // GIVEN
    // ========================

    @Given("the user opens the OpenCart login page")
    public void openLoginPage() {
        UiActions ui = new UiActions(cfg.timeout());
        loginPage = new OpenCartLoginPage(ui).open();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    // ========================
    // THEN (UI VALIDATION)
    // ========================

    @Then("the OpenCart login page should be loaded")
    public void assertLoginPageLoaded() {
        loginPage = new OpenCartLoginPage(new UiActions(cfg.timeout()));
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @Then("the returning customer section should be displayed")
    public void assertReturningCustomerSection() {
        assertThat(loginPage.isReturningCustomerSectionVisible()).isTrue();
    }

    @Then("the email field should be displayed")
    public void assertEmailField() {
        assertThat(loginPage.isEmailFieldVisible()).isTrue();
    }

    @Then("the password field should be displayed")
    public void assertPasswordField() {
        assertThat(loginPage.isPasswordFieldVisible()).isTrue();
    }

    @Then("the login button should be displayed")
    public void assertLoginButton() {
        assertThat(loginPage.isLoginButtonVisible()).isTrue();
    }

    @Then("the forgotten password link should be displayed")
    public void assertForgottenLink() {
        assertThat(loginPage.isForgottenPasswordLinkVisible()).isTrue();
    }

    // ========================
    // NAVIGATION
    // ========================

    @When("the user navigates to the forgotten password page")
    public void navigateToForgottenPassword() {
        forgottenPage = loginPage.goToForgottenPassword();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @Then("the forgotten password page should be loaded")
    public void assertForgottenPageLoaded() {
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user navigates to the register account page")
    public void navigateToRegisterPage() {
        registerPage = loginPage.goToRegisterAccount();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @Then("the register account page should be loaded")
    public void assertRegisterPageLoaded() {
        assertThat(registerPage.isLoaded()).isTrue();
    }

    // ========================
    // ACTIONS
    // ========================

    @When("the user submits the login form without credentials")
    public void submitWithoutCredentials() {
        loginPage.clickLogin();
    }

    @When("the user enters a valid login email")
    public void enterValidEmail() {
        loginPage.enterEmail(credentials.getLoginEmail());
    }

    @When("the user enters a valid login password")
    public void enterValidPassword() {
        loginPage.enterPassword(credentials.getLoginPassword());
    }

    @When("the user submits the login form without password")
    public void submitWithoutPassword() {
        loginPage.clickLogin();
    }

    @When("the user submits the login form without email")
    public void submitWithoutEmail() {
        loginPage.clickLogin();
    }

    @When("the user enters an invalid login email")
    public void enterInvalidEmail() {
        loginPage.enterEmail("invalid@email");
    }

    @When("the user enters an invalid login password")
    public void enterInvalidPassword() {
        loginPage.enterPassword("wrongpass");
    }

    @When("the user enters an unregistered login email")
    public void enterUnregisteredEmail() {
        loginPage.enterEmail("unknownuser@test.com");
    }

    @When("the user submits the login form")
    public void submitLoginForm() {
        loginPage.clickLogin();
    }

    // ========================
    // ASSERTIONS
    // ========================

    @Then("a login warning message should be displayed")
    public void assertLoginWarning() {
        assertThat(loginPage.isLoginWarningVisible()).isTrue();
    }
    
    @Then("a login browser Native message should be displayed")
    public void assertLoginNativeValidationAlert() {
        assertThat(loginPage.isbrowserNativeValidationAlert()).isFalse();
    }

    @Then("the user should be logged in successfully")
    public void assertSuccessfulLogin() {
        assertThat(loginPage.isLoginSuccessful()).isTrue();
    }

    @Then("the login password field should mask the entered value")
    public void assertPasswordMasked() {
        assertThat(loginPage.isPasswordMasked()).isTrue();
    }
    
    
    
    @Then("the login page breadcrumb should display {string}")
    public void assertLoginBreadcrumbItem(String expectedText) {
        assertThat(loginPage.hasBreadcrumbItem(expectedText)).isTrue();
    }

    @When("the user clicks the side menu register link on login page")
    public void clickSideMenuRegisterLink() {
        registerPage = loginPage.goToSideMenuRegister();
        assertThat(registerPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu forgotten password link on login page")
    public void clickSideMenuForgottenPasswordLink() {
        forgottenPage = loginPage.goToSideMenuForgottenPassword();
        assertThat(forgottenPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu login link on login page")
    public void clickSideMenuLoginLink() {
        loginPage = loginPage.goToSideMenuLogin();
        assertThat(loginPage.isLoaded()).isTrue();
    }
    
    @When("the user sets the browser viewport to desktop size")
    public void setDesktopViewport() {
        loginPage.setDesktopViewport();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to tablet size")
    public void setTabletViewport() {
        loginPage.setTabletViewport();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to mobile size")
    public void setMobileViewport() {
        loginPage.setMobileViewport();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @Then("the login page form should remain usable")
    public void assertLoginFormUsable() {
        assertThat(loginPage.isFormUsable()).isTrue();
    }

    @Then("the login page primary elements should be visible")
    public void assertLoginPagePrimaryElementsVisible() {
        assertThat(loginPage.isEmailFieldVisible()).isTrue();
        assertThat(loginPage.isPasswordFieldVisible()).isTrue();
        assertThat(loginPage.isLoginButtonVisible()).isTrue();
    }
    
    
    @Then("the login page URL should use HTTPS")
    public void assertLoginPageUsesHttps() {
        assertThat(loginPage.isHttps()).isTrue();
    }

    @When("the user enters malicious login email input")
    public void enterMaliciousLoginEmail() {
        loginPage.enterEmail("<script>alert('x')</script>");
    }

    @When("the user enters malicious login password input")
    public void enterMaliciousLoginPassword() {
        loginPage.enterPassword("' OR 1=1 --");
    }

    @Then("no JavaScript alert should be displayed")
    public void assertNoJavaScriptAlertDisplayed() {
        assertThat(loginPage.hasBrowserAlert()).isFalse();
    }

    @When("the user submits invalid login credentials multiple times")
    public void submitInvalidLoginMultipleTimes() {
        for (int i = 0; i < 3; i++) {
            loginPage.enterEmail("invalid@test.com");
            loginPage.enterPassword("wrongpass123");
            loginPage.clickLogin();
        }
    }

    @Then("the login page should remain stable")
    public void assertLoginPageRemainsStable() {
        assertThat(loginPage.isLoaded()).isTrue();
        assertThat(loginPage.isEmailFieldVisible()).isTrue();
        assertThat(loginPage.isPasswordFieldVisible()).isTrue();
        assertThat(loginPage.isLoginButtonVisible()).isTrue();
    }
    
    @Then("the my account page should be loaded")
    public void assertMyAccountPageLoaded() {
        myAccountPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()));
        assertThat(myAccountPage.isLoaded()).isTrue();
    }

    @When("the user opens the OpenCart my account page directly")
    public void openMyAccountPageDirectly() {
        UiActions ui = new UiActions(cfg.timeout());
        myAccountPage = new OpenCartMyAccountPage(ui).open();
    }

    @When("the user opens the OpenCart edit account page directly")
    public void openEditAccountPageDirectly() {
        UiActions ui = new UiActions(cfg.timeout());
        new OpenCartEditAccountPage(ui).open();
    }

    @When("the user opens the OpenCart change password page directly")
    public void openChangePasswordPageDirectly() {
        UiActions ui = new UiActions(cfg.timeout());
        new OpenCartChangePasswordPage(ui).open();
    }

    @Then("the user should be redirected to the login page")
    public void assertRedirectedToLoginPage() {
        loginPage = new OpenCartLoginPage(new UiActions(cfg.timeout()));
        assertThat(loginPage.isAtLoginPage()).isTrue();
    }

    @When("the user logs out from the account area")
    public void logoutFromAccountArea() {
        myAccountPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()));
        loginPage = myAccountPage.logout();
        
        
    }

    @Then("the user should be logged out successfully")
    public void assertLoggedOutSuccessfully() {
        assertThat(loginPage.isAtLogoutPage()).isTrue();
    }

    @When("the user opens the OpenCart login page again")
    public void openLoginPageAgain() {
        UiActions ui = new UiActions(cfg.timeout());
        loginPage = new OpenCartLoginPage(ui).open();
    }

    @Then("the application should handle the authenticated login-page access correctly")
    public void assertAuthenticatedLoginPageAccessHandled() {
        assertThat(loginPage.isAtLoginPage() || loginPage.isAuthenticatedRedirectHandled()).isTrue();
    }
    
    
}