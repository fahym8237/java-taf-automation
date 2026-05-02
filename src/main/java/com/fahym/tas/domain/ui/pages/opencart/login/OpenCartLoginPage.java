package com.fahym.tas.domain.ui.pages.opencart.login;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;

import org.openqa.selenium.WebElement;


import com.fahym.tas.core.driver.DriverManager;
import org.openqa.selenium.By;

public final class OpenCartLoginPage extends BasePage {

    // ==== Locators (from pilot spec) ====
    private static final By loginForm = Locators.id("form-login");
    private static final By emailInput = Locators.id("input-email");
    private static final By passwordInput = Locators.id("input-password");
    private static final By loginButton = Locators.xpath("//button[normalize-space()='Login']");
    private static final By forgottenPasswordLink = Locators.linkText("Forgotten Password");
    private static final By registerAccountLink = Locators.linkText("Register");
    private static final By returningCustomerHeader = Locators.xpath("//h2[normalize-space()='Returning Customer']");
    private static final By warningAlert = Locators.css(".alert-danger");
    private static final By myAcount = Locators.xpath("(//h1[normalize-space()='My Account'])[1]");
    
    private static final By breadcrumbItems = Locators.xpath("//li[@class='breadcrumb-item']");
  
    private static final By sideMenuLoginLink = Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Login']");
    private static final By sideMenuRegisterLink = Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Register']");
    private static final By sideMenuForgottenPasswordLink = Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Forgotten Password']");
    private static final By accountLogout = Locators.xpath("//h1[normalize-space()='Account Logout']");
    
    private static final int DESKTOP_WIDTH = 1440;
    private static final int DESKTOP_HEIGHT = 900;

    private static final int TABLET_WIDTH = 768;
    private static final int TABLET_HEIGHT = 1024;

    private static final int MOBILE_WIDTH = 390;
    private static final int MOBILE_HEIGHT = 844;
    
    
    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    public OpenCartLoginPage(UiActions ui) {
        super(ui);
    }

    public OpenCartLoginPage open() {
       ui.open(urls.getloginPageUrl());
    	
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
    
    public OpenCartRegisterPage goToRegisterAccount() {
    	ui.click(registerAccountLink);
    	return  new OpenCartRegisterPage(ui);
    }

    public String returningCustomerHeaderText() {
        return ui.text(returningCustomerHeader);
    }
    
    public boolean isReturningCustomerSectionVisible() {
        return ui.isVisible(returningCustomerHeader);
    }

    public boolean isEmailFieldVisible() {
        return ui.isVisible(emailInput);
    }

    public boolean isPasswordFieldVisible() {
        return ui.isVisible(passwordInput);
    }

    public boolean isLoginButtonVisible() {
        return ui.isVisible(loginButton);
    }

    public boolean isForgottenPasswordLinkVisible() {
        return ui.isVisible(forgottenPasswordLink);
    }
    
    public boolean isLoginWarningVisible() {
        return ui.isVisible(warningAlert);
    }

    public String loginWarningText() {
        return ui.text(warningAlert);
    }
    
    public boolean isLoginSuccessful() {
        return ui.isVisible(myAcount);
    }
    
    public boolean isPasswordMasked() {
        return "password".equalsIgnoreCase(
            ui.attribute(passwordInput, "type")
        );
    }
    
    public boolean hasBreadcrumbItem(String expectedText) {
    
    return ui.elements(breadcrumbItems)
             .stream()
             .map(WebElement::getText)
             .anyMatch(text -> text != null && text.trim().equals(expectedText));
}

    
    public OpenCartRegisterPage goToSideMenuRegister() {
        ui.click(sideMenuRegisterLink);
        return new OpenCartRegisterPage(ui);
    }

    public OpenCartForgottenPage goToSideMenuForgottenPassword() {
        ui.click(sideMenuForgottenPasswordLink);
        return new OpenCartForgottenPage(ui);
    }

    public OpenCartLoginPage goToSideMenuLogin() {
        ui.click(sideMenuLoginLink);
        return new OpenCartLoginPage(ui);
    }
    
    public OpenCartLoginPage setDesktopViewport() {
        ui.setViewportSize(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        return this;
    }

    public OpenCartLoginPage setTabletViewport() {
        ui.setViewportSize(TABLET_WIDTH, TABLET_HEIGHT);
        return this;
    }

    public OpenCartLoginPage setMobileViewport() {
        ui.setViewportSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        return this;
    }

    public boolean isFormUsable() {
        return ui.isVisible(loginForm)
                && ui.isVisible(emailInput)
                && ui.isVisible(passwordInput)
                && ui.isVisible(loginButton);
    }
    
    public boolean isHttps() {
        return ui.currentUrl().startsWith("https://");
    }

    public boolean hasBrowserAlert() {
        try {
            DriverManager.getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isbrowserNativeValidationAlert() {
    	return ui.isValid(emailInput);
    }
    
    public boolean isAtLoginPage() {
        return ui.elements(returningCustomerHeader)
                .stream()
                .map(WebElement::getText)
                .anyMatch(text -> text != null && text.trim().equals("Returning Customer")) ;
    }
    
    public boolean isAtLogoutPage() {
    	return ui.elements(accountLogout)
                .stream()
                .map(WebElement::getText)
                .anyMatch(text -> text != null && text.trim().equals("Account Logout")) ;
    }

    public boolean isAuthenticatedRedirectHandled() {
        return ui.currentUrl().contains("route=account/account");
    }
}