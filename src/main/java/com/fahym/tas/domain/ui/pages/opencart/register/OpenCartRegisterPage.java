package com.fahym.tas.domain.ui.pages.opencart.register;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import org.openqa.selenium.By;

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
    private static final By privacyPolicyLink = Locators.xpath("//a[contains(@href,'privacy') and contains(text(),'Privacy Policy')]");

    private static final By continueButton = Locators.xpath("//form[@id='form-register']//button[@type='submit' and normalize-space()='Continue']");
    private static final By loginPageLink = Locators.xpath("//p//a[contains(@href,'account/login')]");

    public OpenCartRegisterPage(UiActions ui) {
        super(ui);
    }

    public OpenCartRegisterPage open() {
        ui.open(OpenCartUrls.REGISTER);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(contentContainer) && ui.isVisible(registerHeader) && ui.isVisible(registerForm);
    }

    public OpenCartRegisterPage setFirstName(String v) { ui.type(firstNameInput, v); return this; }
    public OpenCartRegisterPage setLastName(String v) { ui.type(lastNameInput, v); return this; }
    public OpenCartRegisterPage setEmail(String v) { ui.type(emailInput, v); return this; }
    public OpenCartRegisterPage setPassword(String v) { ui.type(passwordInput, v); return this; }

    public OpenCartRegisterPage setNewsletter(boolean enabled) {
        // checkbox: click only if needed; simplest for pilot: click when enabled
        if (enabled) ui.click(newsletterCheckbox);
        return this;
    }

    public OpenCartRegisterPage agreePrivacyPolicy() {
        ui.click(privacyPolicyCheckbox);
        return this;
    }

    public OpenCartRegisterPage submit() {
        ui.click(continueButton);
        return this;
    }

    public boolean isPrivacyPolicyLinkVisible() { return ui.isVisible(privacyPolicyLink); }
    public boolean isLoginLinkVisible() { return ui.isVisible(loginPageLink); }

    public boolean isFirstNameErrorVisible() { return ui.isVisible(firstNameError); }
    public String firstNameErrorText() { return ui.text(firstNameError); }

    public boolean isLastNameErrorVisible() { return ui.isVisible(lastNameError); }
    public String lastNameErrorText() { return ui.text(lastNameError); }

    public boolean isEmailErrorVisible() { return ui.isVisible(emailError); }
    public String emailErrorText() { return ui.text(emailError); }

    public boolean isPasswordErrorVisible() { return ui.isVisible(passwordError); }
    public String passwordErrorText() { return ui.text(passwordError); }
}