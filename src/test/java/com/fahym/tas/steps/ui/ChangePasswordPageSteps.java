package com.fahym.tas.steps.ui;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.core.utils.Credentials;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.account.OpenCartMyAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.password.OpenCartChangePasswordPage;


import com.fahym.tas.domain.ui.pages.opencart.edit.OpenCartEditAccountPage;

import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangePasswordPageSteps {

    private final Config cfg = ConfigLoader.load();
    private static Credentials credentials = Credentials.getInstance();


    private OpenCartChangePasswordPage changePasswordPage;
    private OpenCartMyAccountPage myAccountPage;
    private OpenCartLoginPage loginPage;
    private OpenCartEditAccountPage editAccountPage;
    
   
    
    private static final String DIFF_CONF_PASSWWORD = "DiffConfPassword123!";
    
    

    @Given("the user is logged in")
    public void userIsLoggedIn() {
    	
        UiActions ui = new UiActions(cfg.timeout());

        loginPage = new OpenCartLoginPage(ui).open();
        System.out.printf("LAST LOGIN EMAIL: ");
        System.out.println(credentials.getLoginEmail());
        
        System.out.printf("LAST LOGIN PASSWORD: ");
        System.out.println(credentials.getLoginPassword());
        
        loginPage.enterEmail(credentials.getLoginEmail());
        loginPage.enterPassword(credentials.getLoginPassword());
        loginPage.clickLogin();
        
        
        
        //System.out.printf("NEW LOGIN PASSWORD: ");
        //System.out.println(credentials.getLoginNewPassword());
        assertThat(loginPage.isLoginSuccessful()).isTrue();
    }
    
    @Given("the user navigates to the change password page")
    public void navigateToChangePasswordPage() {
        changePasswordPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()))
                .goToChangePassword();

        assertThat(changePasswordPage.isLoaded()).isTrue();
    }
    
    

    @Then("the OpenCart change password page should be loaded")
    public void assertChangePasswordPageLoaded() {
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @Then("the password field should be displayed on change password page")
    public void assertPasswordFieldDisplayed() {
        assertThat(changePasswordPage.isPasswordFieldVisible()).isTrue();
    }

    @Then("the password confirm field should be displayed on change password page")
    public void assertConfirmPasswordFieldDisplayed() {
        assertThat(changePasswordPage.isConfirmFieldVisible()).isTrue();
    }

    @Then("the continue button should be displayed on change password page")
    public void assertContinueButtonDisplayed() {
        assertThat(changePasswordPage.isContinueButtonVisible()).isTrue();
    }

    @Then("the back button should be displayed on change password page")
    public void assertBackButtonDisplayed() {
        assertThat(changePasswordPage.isBackButtonVisible()).isTrue();
    }

    @When("the user enters a valid new password on change password page")
    public void enterValidNewPassword() {
        changePasswordPage.enterPassword(credentials.getLoginNewPassword());
    }

    @When("the user enters the same confirm password on change password page")
    public void enterMatchingConfirmPassword() {
        changePasswordPage.enterConfirm(credentials.getLoginNewPassword());
    }

    @When("the user enters a different confirm password on change password page")
    public void enterDifferentConfirmPassword() {
        changePasswordPage.enterConfirm(DIFF_CONF_PASSWWORD);
    }

    @When("the user submits the change password form")
    public void submitChangePasswordForm() {
        changePasswordPage.submitContinue();
    }

    @When("the user submits the change password form without entering passwords")
    public void submitWithoutPasswords() {
        changePasswordPage.submitContinue();
    }

    @Then("the password should be changed successfully")
    public void assertPasswordChangedSuccessfully() {
        assertThat(changePasswordPage.isPasswordChangeSuccessful()).isTrue();
        if(changePasswordPage.isPasswordChangeSuccessful()){
        	credentials.setLoginOldPassword(credentials.getLoginPassword());
        	
        	credentials.setLoginPassword(credentials.getLoginNewPassword());
        	credentials.setLoginNewPassword(Credentials.generatePassword());
        	 System.out.printf("LAST LOGIN PASSWORD: ");
             System.out.println(credentials.getLoginPassword());
             
             //System.out.printf("NEW LOGIN PASSWORD: ");
             //System.out.println(credentials.getLoginNewPassword());
        }
        
    }

    @Then("a password validation error should be displayed on change password page")
    public void assertPasswordValidationErrorDisplayed() {
        assertThat(changePasswordPage.isPasswordErrorVisible()).isTrue();
    }

    @Then("a confirm password validation error should be displayed on change password page")
    public void assertConfirmValidationErrorDisplayed() {
        assertThat(changePasswordPage.isConfirmErrorVisible()).isTrue();
    }

    @Then("a password mismatch validation error should be displayed on change password page")
    public void assertPasswordMismatchValidationErrorDisplayed() {
        assertThat(changePasswordPage.isConfirmErrorVisible()).isTrue();
    }

    @Then("the password field should mask the entered value on change password page")
    public void assertPasswordMasked() {
        assertThat(changePasswordPage.isPasswordMasked()).isTrue();
    }

    @Then("the confirm password field should mask the entered value on change password page")
    public void assertConfirmPasswordMasked() {
        assertThat(changePasswordPage.isConfirmMasked()).isTrue();
    }

    @When("the user clicks the back button on change password page")
    public void clickBackButton() {
        myAccountPage = changePasswordPage.goBackToMyAccount();
        assertThat(myAccountPage.isLoaded()).isTrue();
    }

    @Then("the my account page should be loaded from change password flow")
    public void assertMyAccountPageLoadedFromChangePasswordFlow() {
        assertThat(myAccountPage.isLoaded()).isTrue();
    }
    
    

    @Then("the change password page breadcrumb should display {string}")
    public void assertChangePasswordBreadcrumbItem(String expectedText) {
        assertThat(changePasswordPage.hasBreadcrumbItem(expectedText)).isTrue();
    }

    @When("the user clicks the side menu my account link on change password page")
    public void clickSideMenuMyAccountLink() {
        myAccountPage = changePasswordPage.goToSideMenuMyAccount();
        assertThat(myAccountPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu edit account link on change password page")
    public void clickSideMenuEditAccountLink() {
        editAccountPage = changePasswordPage.goToSideMenuEditAccount();
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu password link on change password page")
    public void clickSideMenuPasswordLink() {
        changePasswordPage = changePasswordPage.goToSideMenuPassword();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @When("the user clicks the side menu logout link on change password page")
    public void clickSideMenuLogoutLink() {
        loginPage = changePasswordPage.goToSideMenuLogout();
        
    }

    @Then("the edit account page should be loaded from change password flow")
    public void assertEditAccountPageLoadedFromChangePasswordFlow() {
        assertThat(editAccountPage.isLoaded()).isTrue();
    }

    @Then("the user should be logged out successfully from change password flow")
    public void assertLoggedOutSuccessfullyFromChangePasswordFlow() {
    	assertThat(loginPage.isAtLogoutPage()).isTrue();
    }
    
    @Then("the change password page URL should use HTTPS")
    public void assertChangePasswordPageUsesHttps() {
        assertThat(changePasswordPage.isHttps()).isTrue();
    }

    @When("the user enters malicious password input on change password page")
    public void enterMaliciousPasswordInput() {
        changePasswordPage.enterPassword("<script>alert('x')</script>");
    }

    @When("the user enters malicious confirm input on change password page")
    public void enterMaliciousConfirmInput() {
        changePasswordPage.enterConfirm("' OR 1=1 --");
    }

    @Then("the change password page should remain stable")
    public void assertChangePasswordPageRemainsStable() {
        assertThat(changePasswordPage.isLoaded()).isTrue();
        assertThat(changePasswordPage.isPasswordFieldVisible()).isTrue();
        assertThat(changePasswordPage.isConfirmFieldVisible()).isTrue();
        assertThat(changePasswordPage.isContinueButtonVisible()).isTrue();
    }

    @Then("no JavaScript alert should be displayed on change password page")
    public void assertNoJavaScriptAlertDisplayedOnChangePasswordPage() {
        assertThat(changePasswordPage.hasBrowserAlert()).isFalse();
    }

    @Given("the user is not authenticated")
    public void userIsNotAuthenticated() {
        UiActions ui = new UiActions(cfg.timeout());
        loginPage = new OpenCartLoginPage(ui).open();
        assertThat(loginPage.isLoaded()).isTrue();
    }

    @When("the user tries to open the OpenCart change password page directly")
    public void openChangePasswordPageDirectlyWithoutAuthentication() {
        UiActions ui = new UiActions(cfg.timeout());
        changePasswordPage = new OpenCartChangePasswordPage(ui).open();
    }

    @Then("the user should be redirected to the login page from change password flow")
    public void assertRedirectedToLoginPageFromChangePasswordFlow() {
        loginPage = new OpenCartLoginPage(new UiActions(cfg.timeout()));
        assertThat(loginPage.isAtLoginPage()).isTrue();
    }
    
    
    @When("the user sets the browser viewport to desktop size on change password page")
    public void setDesktopViewportOnChangePasswordPage() {
        changePasswordPage.setDesktopViewport();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to tablet size on change password page")
    public void setTabletViewportOnChangePasswordPage() {
        changePasswordPage.setTabletViewport();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @When("the user sets the browser viewport to mobile size on change password page")
    public void setMobileViewportOnChangePasswordPage() {
        changePasswordPage.setMobileViewport();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @Then("the change password form should remain usable")
    public void assertChangePasswordFormUsable() {
        assertThat(changePasswordPage.isFormUsable()).isTrue();
    }

    @Then("the change password page primary elements should be visible")
    public void assertChangePasswordPrimaryElementsVisible() {
        assertThat(changePasswordPage.isPasswordFieldVisible()).isTrue();
        assertThat(changePasswordPage.isConfirmFieldVisible()).isTrue();
        assertThat(changePasswordPage.isContinueButtonVisible()).isTrue();
    }
    
    @When("the user logs out from the account area after password change")
    public void logoutFromAccountAreaAfterPasswordChange() {
        myAccountPage = new OpenCartMyAccountPage(new UiActions(cfg.timeout()));
        loginPage = myAccountPage.logout();
        assertThat(loginPage.isAtLogoutPage()).isTrue();
    }

    @When("the user logs in with the newly changed password")
    public void loginWithNewlyChangedPassword() {
    	UiActions ui = new UiActions(cfg.timeout());
    	loginPage = new OpenCartLoginPage(ui).open();
        loginPage.enterEmail(credentials.getLoginEmail());
        loginPage.enterPassword(credentials.getLoginPassword());
        loginPage.clickLogin();
    }

    @Then("the user should be logged in successfully after password change")
    public void assertLoggedInSuccessfullyAfterPasswordChange() {
        assertThat(loginPage.isLoginSuccessful()).isTrue();
    }

    @When("the user logs in with the old password after password change")
    public void loginWithOldPasswordAfterPasswordChange() {
    	UiActions ui = new UiActions(cfg.timeout());
    	loginPage = new OpenCartLoginPage(ui).open();
        loginPage.enterEmail(credentials.getLoginEmail());
        loginPage.enterPassword(credentials.getLoginOldPassword());
        loginPage.clickLogin();
    }

    @Then("a login warning message should be displayed after password change")
    public void assertLoginWarningDisplayedAfterPasswordChange() {
        assertThat(loginPage.isLoginWarningVisible()).isTrue();
        /*if(loginPage.isLoginWarningVisible()){
        	credentials.setLoginPassword(credentials.getLoginNewPassword());
        	credentials.setLoginNewPassword(Credentials.generatePassword());
        	 System.out.printf("LAST LOGIN PASSWORD: ");
             System.out.println(credentials.getLoginPassword());
             
             System.out.printf("NEW LOGIN PASSWORD: ");
             System.out.println(credentials.getLoginNewPassword());
        }*/
    }

    @When("the user refreshes the change password page")
    public void refreshChangePasswordPage() {
        changePasswordPage.refresh();
        assertThat(changePasswordPage.isLoaded()).isTrue();
    }

    @Then("the password field should be empty on change password page")
    public void assertPasswordFieldEmptyOnChangePasswordPage() {
        assertThat(changePasswordPage.passwordValue()).isEmpty();
    }

    @Then("the confirm password field should be empty on change password page")
    public void assertConfirmFieldEmptyOnChangePasswordPage() {
        assertThat(changePasswordPage.confirmValue()).isEmpty();
    }

    @When("the user navigates forward in the browser after back from change password page")
    public void navigateForwardAfterBackFromChangePasswordPage() {
        changePasswordPage = new OpenCartChangePasswordPage(new UiActions(cfg.timeout()));
        changePasswordPage.goForward();
    }
}