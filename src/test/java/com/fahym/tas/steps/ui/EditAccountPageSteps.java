package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.utils.Credentials;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.account.OpenCartMyAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.edit.OpenCartEditAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.password.OpenCartChangePasswordPage;

import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class EditAccountPageSteps {

    private final Config cfg = ConfigLoader.load();
    private static Credentials credentials = Credentials.getInstance();

    

    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Smith";
    private static final String UPDATED_FIRST_NAME = "John";
    private static final String UPDATED_LAST_NAME = "Smith";

    private static final String INVALID_EMAIL = "invalid@email";
  

    private OpenCartChangePasswordPage changePasswordPage;
    private OpenCartMyAccountPage myAccountPage;
    private OpenCartEditAccountPage editAccountPage;
    private OpenCartLoginPage loginPage;
    
    @Given("the user is logged in to edit account")
    public void userIsLoggedInToEditAcc() {
    	
        UiActions ui = new UiActions(cfg.timeout());

        loginPage = new OpenCartLoginPage(ui).open();
        System.out.printf("LAST LOGIN EMAIL: ");
        System.out.println(credentials.getLoginEmail());
        
        System.out.printf("LAST LOGIN PASSWORD: ");
        System.out.println(credentials.getLoginPassword());
        
        loginPage.enterEmail(credentials.getLoginEmail());
        loginPage.enterPassword(credentials.getLoginPassword());
        loginPage.clickLogin();
    }

    @Given("the user navigates to the edit account page")
    public void navigateToEditAccountPage() {
        myAccountPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()));
        editAccountPage = myAccountPage.goToEditAccount();

        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @Then("the OpenCart edit account page should be loaded")
    public void assertEditAccountPageLoaded() {
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @Then("the first name field should be displayed on edit account page")
    public void assertFirstNameFieldDisplayed() {
        assertThat(editAccountPage.isFirstNameFieldVisible()).isTrue();
    }

    @Then("the last name field should be displayed on edit account page")
    public void assertLastNameFieldDisplayed() {
        assertThat(editAccountPage.isLastNameFieldVisible()).isTrue();
    }

    @Then("the email field should be displayed on edit account page")
    public void assertEmailFieldDisplayed() {
        assertThat(editAccountPage.isEmailFieldVisible()).isTrue();
    }

    @Then("the continue button should be displayed on edit account page")
    public void assertContinueButtonDisplayed() {
        assertThat(editAccountPage.isContinueButtonVisible()).isTrue();
    }

    @Then("the back button should be displayed on edit account page")
    public void assertBackButtonDisplayed() {
        assertThat(editAccountPage.isBackButtonVisible()).isTrue();
    }

    @Then("the first name field should contain the current customer first name")
    public void assertFirstNamePrefilled() {
        assertThat(editAccountPage.firstNameValue()).isNotBlank();
    }

    @Then("the last name field should contain the current customer last name")
    public void assertLastNamePrefilled() {
        assertThat(editAccountPage.lastNameValue()).isNotBlank();
    }

    @Then("the email field should contain the current customer email")
    public void assertEmailPrefilled() {
        assertThat(editAccountPage.emailValue()).isNotBlank();
        assertThat(editAccountPage.emailValue()).contains("@");
    }

    @When("the user updates the first name on edit account page with a valid value")
    public void updateFirstNameWithValidValue() {
        editAccountPage.clearFirstName();
        editAccountPage.enterFirstName(VALID_FIRST_NAME);
    }

    @When("the user updates the last name on edit account page with a valid value")
    public void updateLastNameWithValidValue() {
        editAccountPage.clearLastName();
        editAccountPage.enterLastName(VALID_LAST_NAME);
    }

    @When("the user updates the email on edit account page with a unique valid value")
    public void updateEmailWithUniqueValidValue() {
        editAccountPage.clearEmail();
        String generateEmail = Credentials.generateTestEmail();
        editAccountPage.enterEmail(generateEmail);
        credentials.setLoginNewEmail(generateEmail);
    }

    @When("the user updates the email on edit account page with an invalid value")
    public void updateEmailWithInvalidValue() {
        editAccountPage.clearEmail();
        editAccountPage.enterEmail(INVALID_EMAIL);
    }

    @When("the user updates the email on edit account page with a duplicate value")
    public void updateEmailWithDuplicateValue() {
        editAccountPage.clearEmail();
        editAccountPage.enterEmail(credentials.getLoginEmail());
    }

    @When("the user clears the first name field on edit account page")
    public void clearFirstNameField() {
        editAccountPage.clearFirstName();
    }

    @When("the user clears the last name field on edit account page")
    public void clearLastNameField() {
        editAccountPage.clearLastName();
    }

    @When("the user clears the email field on edit account page")
    public void clearEmailField() {
        editAccountPage.clearEmail();
    }

    @When("the user submits the edit account form")
    public void submitEditAccountForm() {
        editAccountPage.submitContinue();
    }

    @Then("the account information should be updated successfully")
    public void assertAccountInformationUpdatedSuccessfully() {
        assertThat(editAccountPage.isUpdateSuccessful()).isTrue();
        if(editAccountPage.isUpdateSuccessful()) {
        	credentials.setLoginEmail(credentials.getLoginNewEmail());
        	System.out.printf("LAST LOGIN EMAIL: ");
            System.out.println(credentials.getLoginEmail());
        }
    }

    @Then("a first name validation error should be displayed on edit account page")
    public void assertFirstNameValidationErrorDisplayed() {
        assertThat(editAccountPage.isFirstNameErrorVisible()).isTrue();
    }

    @Then("a last name validation error should be displayed on edit account page")
    public void assertLastNameValidationErrorDisplayed() {
        assertThat(editAccountPage.isLastNameErrorVisible()).isTrue();
    }

    @Then("an email validation error should be displayed on edit account page")
    public void assertEmailValidationErrorDisplayed() {
        assertThat(editAccountPage.isEmailErrorVisible()).isTrue();
    }

    
    
    @Then("no email business validation error should be displayed on edit account page")
    public void assertNoEmailBusinessValidationErrorDisplayed() {
        assertThat(editAccountPage.isEmailErrorVisible() && editAccountPage.isWarningVisible()).isFalse();
    }

    @When("the user clicks the back button on edit account page")
    public void clickBackButtonOnEditAccountPage() {
        myAccountPage = editAccountPage.goBackToMyAccount();
        assertThat(myAccountPage.isLoaded()).isTrue();
    }

    @Then("the my account page should be loaded from edit account flow")
    public void assertMyAccountPageLoadedFromEditAccountFlow() {
        assertThat(myAccountPage.isLoaded()).isTrue();
    }
    
    @Then("the edit account page breadcrumb should display {string}")
    public void assertEditAccountBreadcrumbItem(String expectedText) {
        assertThat(editAccountPage.hasBreadcrumbItem(expectedText)).isTrue();
    }

    @When("the user clicks the side menu my account link on edit account page")
    public void clickSideMenuMyAccountLinkOnEditAccountPage() {
        myAccountPage = editAccountPage.goToSideMenuMyAccount();
        assertThat(myAccountPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu edit account link on edit account page")
    public void clickSideMenuEditAccountLinkOnEditAccountPage() {
        editAccountPage = editAccountPage.goToSideMenuEditAccount();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu password link on edit account page")
    public void clickSideMenuPasswordLinkOnEditAccountPage() {
        changePasswordPage = editAccountPage.goToSideMenuPassword();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu logout link on edit account page")
    public void clickSideMenuLogoutLinkOnEditAccountPage() {
        loginPage = editAccountPage.goToSideMenuLogout();
        assertThat(loginPage.isAtLogoutPage()).isTrue();
    }

    @Then("the change password page should be loaded from edit account flow")
    public void assertChangePasswordPageLoadedFromEditAccountFlow() {
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @Then("the user should be logged out successfully from edit account flow")
    public void assertLoggedOutSuccessfullyFromEditAccountFlow() {
        assertThat(loginPage.isAtLogoutPage()).isTrue();
    }
    
    @Then("the edit account page URL should use HTTPS")
    public void assertEditAccountPageUsesHttps() {
        assertThat(editAccountPage.isHttps()).isTrue();
    }

    @When("the user enters malicious first name input on edit account page")
    public void enterMaliciousFirstNameOnEditAccountPage() {
        editAccountPage.clearFirstName();
        editAccountPage.enterFirstName("<script>alert('x')</script>");
    }

    @When("the user enters malicious last name input on edit account page")
    public void enterMaliciousLastNameOnEditAccountPage() {
        editAccountPage.clearLastName();
        editAccountPage.enterLastName("' OR 1=1 --");
    }

    @When("the user enters malicious email input on edit account page")
    public void enterMaliciousEmailOnEditAccountPage() {
        editAccountPage.clearEmail();
        editAccountPage.enterEmail("<script>@test.com");
    }

    @When("the user enters a very long first name on edit account page")
    public void enterVeryLongFirstNameOnEditAccountPage() {
        editAccountPage.clearFirstName();
        editAccountPage.enterFirstName("A".repeat(300));
    }

    @When("the user enters a very long last name on edit account page")
    public void enterVeryLongLastNameOnEditAccountPage() {
        editAccountPage.clearLastName();
        editAccountPage.enterLastName("B".repeat(300));
    }

    @When("the user enters a very long email on edit account page")
    public void enterVeryLongEmailOnEditAccountPage() {
        editAccountPage.clearEmail();
        editAccountPage.enterEmail("verylongemail" + "x".repeat(260) + "@test.com");
    }

    @Then("the edit account page should remain stable")
    public void assertEditAccountPageRemainsStable() {
        assertThat(editAccountPage.isLoaded()).isTrue();
        assertThat(editAccountPage.isFirstNameFieldVisible()).isTrue();
        assertThat(editAccountPage.isLastNameFieldVisible()).isTrue();
        assertThat(editAccountPage.isEmailFieldVisible()).isTrue();
        assertThat(editAccountPage.isContinueButtonVisible()).isTrue();
    }

    @When("the user tries to open the OpenCart edit account page directly")
    public void openEditAccountPageDirectlyWithoutAuthentication() {
        UiActions ui = new UiActions(cfg.timeout());
        editAccountPage = new OpenCartEditAccountPage(ui).open();
    }

    @Then("the user should be redirected to the login page from edit account flow")
    public void assertRedirectedToLoginPageFromEditAccountFlow() {
        loginPage = new OpenCartLoginPage(new UiActions(cfg.timeout()));
        assertThat(loginPage.isAtLoginPage()).isTrue();
    }
    
    @When("the user sets the browser viewport to desktop size on edit account page")
    public void setDesktopViewportOnEditAccountPage() {
        editAccountPage.setDesktopViewport();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to tablet size on edit account page")
    public void setTabletViewportOnEditAccountPage() {
        editAccountPage.setTabletViewport();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to mobile size on edit account page")
    public void setMobileViewportOnEditAccountPage() {
        editAccountPage.setMobileViewport();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @Then("the edit account form should remain usable")
    public void assertEditAccountFormUsable() {
        assertThat(editAccountPage.isFormUsable()).isTrue();
    }

    @Then("the edit account page primary elements should be visible")
    public void assertEditAccountPrimaryElementsVisible() {
        assertThat(editAccountPage.isFirstNameFieldVisible()).isTrue();
        assertThat(editAccountPage.isLastNameFieldVisible()).isTrue();
        assertThat(editAccountPage.isEmailFieldVisible()).isTrue();
        assertThat(editAccountPage.isContinueButtonVisible()).isTrue();
    }
    
    
    
    
    @When("the user navigates again to the edit account page")
    public void navigateAgainToEditAccountPage() {
        myAccountPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()));
        editAccountPage = myAccountPage.goToEditAccount();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @Then("the updated first name should be displayed on edit account page")
    public void assertUpdatedFirstNameDisplayed() {
        assertThat(editAccountPage.firstNameValue()).isEqualTo(UPDATED_FIRST_NAME);
    }

    @Then("the updated last name should be displayed on edit account page")
    public void assertUpdatedLastNameDisplayed() {
        assertThat(editAccountPage.lastNameValue()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Then("the updated email should be displayed on edit account page")
    public void assertUpdatedEmailDisplayed() {
        assertThat(editAccountPage.emailValue()).isEqualTo(credentials.getLoginEmail());
    }

    @When("the user refreshes the edit account page")
    public void refreshEditAccountPage() {
        editAccountPage.refresh();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @When("the user navigates forward in the browser after back from edit account page")
    public void navigateForwardAfterBackFromEditAccountPage() {
        editAccountPage = new OpenCartEditAccountPage(new UiActions(cfg.timeout()));
        editAccountPage.goForward();
    }
}