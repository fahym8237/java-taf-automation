package com.fahym.tas.domain.ui.pages.opencart.forgotten;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.register.OpenCartRegisterPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class OpenCartForgottenPage extends BasePage {

    // ==== Locators (from pilot spec) ====
    private static final By contentContainer = Locators.id("content");
    private static final By forgottenPasswordHeader = Locators.xpath("//h1[normalize-space()='Forgot Your Password?']");
    private static final By instructionText = Locators.xpath("//p[contains(text(),'password reset link')]");
    private static final By forgottenForm = Locators.id("form-forgotten");
    private static final By emailLegend = Locators.xpath("//legend[normalize-space()='Your E-Mail Address']");
    private static final By emailInput = Locators.id("input-email");
    private static final By emailError = Locators.xpath("//div[contains(@class,'alert-danger') and contains(text(),'E-Mail Address was not found')]");
    private static final By backButton = Locators.xpath("//a[contains(@href,'account/login') and normalize-space()='Back']");
    private static final By continueButton = Locators.xpath("//form[@id='form-forgotten']//button[@type='submit' and normalize-space()='Continue']");
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
    public OpenCartForgottenPage(UiActions ui) {
        super(ui);
    }

    public OpenCartForgottenPage open() {
        ui.open(urls.getforgottenPageUrl());
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
        return ui.isVisible(emailError) 
            && ui.text(emailError).contains("E-Mail Address was not found");
    }


    public String emailErrorText() {
        return ui.text(emailError);
    }

    public OpenCartLoginPage goBackToLogin() {
        ui.click(backButton);
        return new OpenCartLoginPage(ui);
    }
    
    public boolean isEmailFieldVisible() {
        return ui.isVisible(emailInput);
    }

    public boolean isContinueButtonVisible() {
        return ui.isVisible(continueButton);
    }

    public boolean isBackButtonVisible() {
        return ui.isVisible(backButton);
    }

    public boolean isRequestAccepted() {
        return ui.currentUrl().contains("route=account/login")
                || ui.currentUrl().contains("route=account/forgotten")
                || ui.isVisible(Locators.css(".alert-success"));
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
    
    public boolean hasBrowserAlert() {
        try {
            DriverManager.getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isHttps() {
        return ui.currentUrl().startsWith("https://");
    }

    
    public OpenCartForgottenPage setDesktopViewport() {
        ui.setViewportSize(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        return this;
    }

    public OpenCartForgottenPage setTabletViewport() {
        ui.setViewportSize(TABLET_WIDTH, TABLET_HEIGHT);
        return this;
    }

    public OpenCartForgottenPage setMobileViewport() {
        ui.setViewportSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        return this;
    }

    public boolean isFormUsable() {
        return ui.isVisible(forgottenForm)
                && ui.isVisible(emailInput)
                && ui.isVisible(continueButton)
                && ui.isVisible(backButton);
    }
    
    public OpenCartForgottenPage refresh() {
        DriverManager.getDriver().navigate().refresh();
        ui.waitDocumentReady();
        return this;
    }

    public void goBack() {
        DriverManager.getDriver().navigate().back();
        ui.waitDocumentReady();
    }

    public String emailValue() {
        String value = ui.attribute(emailInput, "value");
        return value == null ? "" : value;
    }
}