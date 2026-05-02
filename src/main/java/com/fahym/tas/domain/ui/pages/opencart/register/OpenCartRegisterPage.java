package com.fahym.tas.domain.ui.pages.opencart.register;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class OpenCartRegisterPage extends BasePage {

    // ==== Locators (from pilot spec) ====
    private static final By contentContainer = Locators.id("content");
    private static final By registerHeader = Locators.xpath("//h1[normalize-space()='Register Account']");
    private static final By registerForm = Locators.id("form-register");

    //private static final By personalDetailsLegend = Locators.xpath("//legend[normalize-space()='Your Personal Details']");
    private static final By firstNameInput = Locators.id("input-firstname");
    private static final By firstNameError = Locators.id("error-firstname");

    private static final By lastNameInput = Locators.id("input-lastname");
    private static final By lastNameError = Locators.id("error-lastname");

    private static final By emailInput = Locators.id("input-email");
    private static final By emailError = Locators.id("error-email");

    //private static final By passwordLegend = Locators.xpath("//legend[normalize-space()='Your Password']");
    private static final By passwordInput = Locators.id("input-password");
    private static final By passwordError = Locators.id("error-password");

    //private static final By newsletterLegend = Locators.xpath("//legend[normalize-space()='Newsletter']");
    private static final By newsletterCheckbox = Locators.id("input-newsletter");

    private static final By privacyPolicyCheckbox = Locators.xpath("//input[@name='agree' and @type='checkbox']");
    private static final By privacyPolicyLink = Locators.xpath("//b[normalize-space()='Privacy Policy']");

    private static final By continueButton = Locators.xpath("//form[@id='form-register']//button[@type='submit' and normalize-space()='Continue']");
    private static final By loginPageLink = Locators.xpath("//p//a[contains(@href,'account/login')]");
    
    private static final By CreatedAccount = Locators.xpath("//h1[normalize-space()='Your Account Has Been Created!']");
    private static final By privacyPolicyWarning = Locators.css(".alert-danger");
    private static final By privacyPolicyBanner = Locators.xpath("//div[@class='modal-header']");
    private static final By breadcrumbItems = Locators.css(".breadcrumb .breadcrumb-item");

    private static final By sideMenuLoginLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Login']");

    private static final By sideMenuRegisterLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Register']");

    private static final By sideMenuForgottenPasswordLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Forgotten Password']");
    
    
    private static final int DESKTOP_WIDTH = 1440;
    private static final int DESKTOP_HEIGHT = 900;

    private static final int TABLET_WIDTH = 768;
    private static final int TABLET_HEIGHT = 1024;

    private static final int MOBILE_WIDTH = 390;
    private static final int MOBILE_HEIGHT = 844;
    
    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    public OpenCartRegisterPage(UiActions ui) {
        super(ui);
    }

    public OpenCartRegisterPage open() {
        ui.open(urls.getregisterPageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(contentContainer) && ui.isVisible(registerHeader) && ui.isVisible(registerForm);
    }

    public OpenCartRegisterPage setFirstName(String v) { 
    	ui.type(firstNameInput, v); 
    return this; 
    }
    public OpenCartRegisterPage setLastName(String v) { ui.type(lastNameInput, v); return this; }
    public OpenCartRegisterPage setEmail(String v) { ui.type(emailInput, v); return this; }
    public OpenCartRegisterPage setPassword(String v) { ui.type(passwordInput, v); return this; }

    public OpenCartRegisterPage setNewsletter(boolean enabled) {
        // checkbox: click only if needed; simplest for pilot: click when enabled
        if (enabled) ui.click(newsletterCheckbox);
        return this;
    }
    
    

    public OpenCartRegisterPage agreePrivacyPolicy() {
        ui.scrollIntoView(privacyPolicyCheckbox);   // ensures checkbox is centered
        ui.jsClick(privacyPolicyCheckbox);          // bypasses overlay issues
        return this;
    }


    public OpenCartRegisterPage submit() { ui.submit(continueButton);return this;} 
    public OpenCartRegisterPage acceptAlert() {ui.acceptAlert(); return this;};

    public boolean isPrivacyPolicyLinkVisible() { return ui.isVisible(privacyPolicyLink); }
    public boolean isLoginLinkVisible() { return ui.isVisible(loginPageLink); }

    public boolean isFirstNameErrorVisible() { 
    	ui.scrollIntoView(firstNameError);
    	return ui.isVisibleNow(firstNameError); }
    public String firstNameErrorText() { return ui.text(firstNameError); }

    public boolean isLastNameErrorVisible() {
    	ui.scrollIntoView(lastNameError);
    	return ui.isVisibleNow(lastNameError); }
    public String lastNameErrorText() { 
    	ui.scrollIntoView(lastNameError);
    	return ui.text(lastNameError); }

    public boolean isEmailErrorVisible() {
    	ui.scrollIntoView(emailError);
    	return ui.isVisibleNow(emailError); }
    public String emailErrorText() { 
    	ui.scrollIntoView(emailError);
    	return ui.text(emailError); }

    public boolean isPasswordErrorVisible() { 
    	ui.scrollIntoView(passwordError);
    	return ui.isVisibleNow(passwordError); }
    public String passwordErrorText() { 
    	ui.scrollIntoView(passwordError);
    	return ui.text(passwordError); }
    
    public String successMessage() {
    	return ui.text(CreatedAccount);
    }
    
    

    public boolean isFirstNameFieldVisible() {
        return ui.isVisible(firstNameInput);
    }

    public boolean isLastNameFieldVisible() {
        return ui.isVisible(lastNameInput);
    }

    public boolean isEmailFieldVisible() {
        return ui.isVisible(emailInput);
    }

    public boolean isPasswordFieldVisible() {
        return ui.isVisible(passwordInput);
    }

    public boolean isContinueButtonVisible() {
        return ui.isVisible(continueButton);
    }

    public OpenCartRegisterPage clearEmail() {
        ui.clear(emailInput);
        return this;
    }

    public boolean isPrivacyPolicyWarningVisible() {
        return ui.isVisibleNow(privacyPolicyWarning) || ui.isVisible(privacyPolicyWarning);
    }

    public boolean isPasswordMasked() {
        return "password".equalsIgnoreCase(ui.attribute(passwordInput, "type"));
    }
    
    public OpenCartLoginPage goToLoginPage() {
        ui.click(loginPageLink);
        return new OpenCartLoginPage(ui);
    }

    public boolean hasBreadcrumbItem(String expectedText) {
        return ui.elements(breadcrumbItems)
                .stream()
                .map(WebElement::getText)
                .anyMatch(text -> text != null && text.trim().equals(expectedText));
    }

    public OpenCartLoginPage goToSideMenuLogin() {
        ui.click(sideMenuLoginLink);
        return new OpenCartLoginPage(ui);
    }

    public OpenCartRegisterPage goToSideMenuRegister() {
        ui.click(sideMenuRegisterLink);
        return new OpenCartRegisterPage(ui);
    }

    public OpenCartForgottenPage goToSideMenuForgottenPassword() {
        ui.click(sideMenuForgottenPasswordLink);
        return new OpenCartForgottenPage(ui);
    }
    
    public OpenCartRegisterPage openPrivacyPolicy() {
        ui.click(privacyPolicyLink);
        return this;
    }

    public boolean isPrivacyPolicyOpened() {
        return ui.isVisible(privacyPolicyBanner);
    }
    
    public OpenCartRegisterPage clearFirstName() {
        ui.clear(firstNameInput);
        return this;
    }

    public OpenCartRegisterPage clearLastName() {
        ui.clear(lastNameInput);
        return this;
    }

    public OpenCartRegisterPage clearPassword() {
        ui.clear(passwordInput);
        return this;
    }

    public boolean isHttps() {
        return ui.currentUrl().startsWith("https://");
    }
    
    public OpenCartRegisterPage setDesktopViewport() {
        ui.setViewportSize(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        return this;
    }

    public OpenCartRegisterPage setTabletViewport() {
        ui.setViewportSize(TABLET_WIDTH, TABLET_HEIGHT);
        return this;
    }

    public OpenCartRegisterPage setMobileViewport() {
        ui.setViewportSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        return this;
    }

    public boolean isFormUsable() {
        return ui.isVisible(registerForm)
                && ui.isVisible(firstNameInput)
                && ui.isVisible(lastNameInput)
                && ui.isVisible(emailInput)
                && ui.isVisible(passwordInput)
                && ui.isVisible(continueButton);
    }
    
    public OpenCartRegisterPage refresh() {
        DriverManager.getDriver().navigate().refresh();
        ui.waitDocumentReady();
        return this;
    }

    public void goBack() {
        DriverManager.getDriver().navigate().back();
        ui.waitDocumentReady();
    }

    public String firstNameValue() {
        String value = ui.attribute(firstNameInput, "value");
        return value == null ? "" : value;
    }

    public String lastNameValue() {
        String value = ui.attribute(lastNameInput, "value");
        return value == null ? "" : value;
    }

    public String emailValue() {
        String value = ui.attribute(emailInput, "value");
        return value == null ? "" : value;
    }

    public String passwordValue() {
        String value = ui.attribute(passwordInput, "value");
        return value == null ? "" : value;
    }
}