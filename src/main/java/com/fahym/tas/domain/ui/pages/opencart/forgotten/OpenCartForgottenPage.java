package com.fahym.tas.domain.ui.pages.opencart.forgotten;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import org.openqa.selenium.By;

public final class OpenCartForgottenPage extends BasePage {

    // ==== Locators (from pilot spec) ====
    private static final By contentContainer = Locators.id("content");
    private static final By forgottenPasswordHeader = Locators.xpath("//h1[normalize-space()='Forgot Your Password?']");
    private static final By instructionText = Locators.xpath("//p[contains(text(),'password reset link')]");
    private static final By forgottenForm = Locators.id("form-forgotten");
    private static final By emailLegend = Locators.xpath("//legend[normalize-space()='Your E-Mail Address']");
    private static final By emailInput = Locators.id("input-email");
    private static final By emailError = Locators.id("error-email");
    private static final By backButton = Locators.xpath("//a[contains(@href,'account/login') and normalize-space()='Back']");
    private static final By continueButton = Locators.xpath("//form[@id='form-forgotten']//button[@type='submit' and normalize-space()='Continue']");

    public OpenCartForgottenPage(UiActions ui) {
        super(ui);
    }

    public OpenCartForgottenPage open() {
        ui.open(OpenCartUrls.FORGOTTEN);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(contentContainer)
                && ui.isVisible(forgottenPasswordHeader)
                && ui.isVisible(forgottenForm)
                && ui.isVisible(emailLegend);
    }

    public String headerText() {
        return ui.text(forgottenPasswordHeader);
    }

    public boolean hasInstructionText() {
        return ui.isVisible(instructionText);
    }

    public OpenCartForgottenPage enterEmail(String email) {
        ui.type(emailInput, email);
        return this;
    }

    public OpenCartForgottenPage submitContinue() {
        ui.click(continueButton);
        return this;
    }

    public boolean isEmailErrorVisible() {
        return ui.isVisible(emailError);
    }

    public String emailErrorText() {
        return ui.text(emailError);
    }

    public OpenCartLoginPage goBackToLogin() {
        ui.click(backButton);
        return new OpenCartLoginPage(ui);
    }
}