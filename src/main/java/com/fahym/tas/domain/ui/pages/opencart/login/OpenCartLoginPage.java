package com.fahym.tas.domain.ui.pages.opencart.login;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import org.openqa.selenium.By;

public final class OpenCartLoginPage extends BasePage {

    // ==== Locators (from pilot spec) ====
    private static final By loginForm = Locators.id("form-login");
    private static final By emailInput = Locators.id("input-email");
    private static final By passwordInput = Locators.id("input-password");
    private static final By loginButton = Locators.xpath("//button[normalize-space()='Login']");
    private static final By forgottenPasswordLink = Locators.linkText("Forgotten Password");
    private static final By returningCustomerHeader = Locators.xpath("//h2[normalize-space()='Returning Customer']");

    public OpenCartLoginPage(UiActions ui) {
        super(ui);
    }

    public OpenCartLoginPage open() {
        ui.open(OpenCartUrls.LOGIN);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(loginForm) && ui.isVisible(returningCustomerHeader);
    }

    public OpenCartLoginPage enterEmail(String email) {
        ui.type(emailInput, email);
        return this;
    }

    public OpenCartLoginPage enterPassword(String password) {
        ui.type(passwordInput, password);
        return this;
    }

    public void clickLogin() {
        ui.click(loginButton);
    }

    public OpenCartForgottenPage goToForgottenPassword() {
        ui.click(forgottenPasswordLink);
        return new OpenCartForgottenPage(ui);
    }

    public String returningCustomerHeaderText() {
        return ui.text(returningCustomerHeader);
    }
}