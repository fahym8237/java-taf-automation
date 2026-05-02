package com.fahym.tas.domain.ui.pages.opencart.edit;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.account.OpenCartMyAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.password.OpenCartChangePasswordPage;
import org.openqa.selenium.WebElement;
import com.fahym.tas.core.driver.DriverManager;
import org.openqa.selenium.By;

public final class OpenCartEditAccountPage extends BasePage {

    // ========= Page container / headers =========
    private static final By formCustomer = Locators.id("form-customer");
    private static final By pageHeader = Locators.xpath("//h1[normalize-space()='My Account Information']");
    private static final By personalDetailsLegend = Locators.xpath("//legend[normalize-space()='Your Personal Details']");

    // ========= Inputs =========
    private static final By firstNameInput = Locators.id("input-firstname");
    private static final By lastNameInput = Locators.id("input-lastname");
    private static final By emailInput = Locators.id("input-email");

    // ========= Validation =========
    private static final By firstNameError = Locators.id("error-firstname");
    private static final By lastNameError = Locators.id("error-lastname");
    private static final By emailError = Locators.id("error-email");
    private static final By warningAlert = Locators.css(".alert-danger");
    private static final By successAlert = Locators.css(".alert-success");

    // ========= Buttons =========
    private static final By backButton =
            Locators.xpath("//a[contains(@href,'account/account') and normalize-space()='Back']");
    private static final By continueButton =
            Locators.xpath("//form[@id='form-customer']//button[@type='submit' and normalize-space()='Continue']");

    
    private static final By breadcrumbItems = Locators.css(".breadcrumb .breadcrumb-item");

    private static final By sideMenuMyAccountLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='My Account']");

    private static final By sideMenuEditAccountLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Edit Account']");

    private static final By sideMenuPasswordLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Password']");

    private static final By sideMenuLogoutLink =
            Locators.xpath("//aside[@id='column-right']//a[normalize-space()='Logout']");
    
    
    private static final int DESKTOP_WIDTH = 1440;
    private static final int DESKTOP_HEIGHT = 900;

    private static final int TABLET_WIDTH = 768;
    private static final int TABLET_HEIGHT = 1024;

    private static final int MOBILE_WIDTH = 390;
    private static final int MOBILE_HEIGHT = 844;
    
    
    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    public OpenCartEditAccountPage(UiActions ui) {
        super(ui);
    }

    public OpenCartEditAccountPage open() {
        ui.open(urls.geteditAccountPageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(formCustomer)
                && ui.isVisible(pageHeader)
                && ui.isVisible(personalDetailsLegend);
    }

    // ========= Visibility =========

    public boolean isFirstNameFieldVisible() {
        return ui.isVisible(firstNameInput);
    }

    public boolean isLastNameFieldVisible() {
        return ui.isVisible(lastNameInput);
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

    // ========= Values =========

    public String firstNameValue() {
        return safeValue(firstNameInput);
    }

    public String lastNameValue() {
        return safeValue(lastNameInput);
    }

    public String emailValue() {
        return safeValue(emailInput);
    }

    // ========= Field actions =========

    public OpenCartEditAccountPage enterFirstName(String value) {
        ui.type(firstNameInput, value);
        return this;
    }

    public OpenCartEditAccountPage enterLastName(String value) {
        ui.type(lastNameInput, value);
        return this;
    }

    public OpenCartEditAccountPage enterEmail(String value) {
        ui.type(emailInput, value);
        return this;
    }

    public OpenCartEditAccountPage clearFirstName() {
        ui.clear(firstNameInput);
        return this;
    }

    public OpenCartEditAccountPage clearLastName() {
        ui.clear(lastNameInput);
        return this;
    }

    public OpenCartEditAccountPage clearEmail() {
        ui.clear(emailInput);
        return this;
    }

    // ========= Form actions =========

    public OpenCartEditAccountPage submitContinue() {
        ui.click(continueButton);
        return this;
    }

    public OpenCartMyAccountPage goBackToMyAccount() {
        ui.click(backButton);
        return new OpenCartMyAccountPage(ui);
    }

    // ========= Validation / Result =========

    public boolean isFirstNameErrorVisible() {
        return isValidationVisible(firstNameError);
    }

    public boolean isLastNameErrorVisible() {
        return isValidationVisible(lastNameError);
    }

    public boolean isEmailErrorVisible() {
        return isValidationVisible(emailError);
    }

    public boolean isWarningVisible() {
        return ui.isVisibleNow(warningAlert) || ui.isVisible(warningAlert);
    }

    public boolean isUpdateSuccessful() {
        return ui.isVisibleNow(successAlert)
                || ui.isVisible(successAlert)
                || ui.currentUrl().contains("route=account/account");
    }

    // ========= Internal helpers =========

    private boolean isValidationVisible(By locator) {
        String text = "";
        try {
            text = ui.text(locator);
        } catch (Exception ignored) {
        }

        return ui.isVisibleNow(locator)
                || ui.isVisible(locator)
                || (text != null && !text.trim().isEmpty());
    }

    private String safeValue(By locator) {
        String value = ui.attribute(locator, "value");
        return value == null ? "" : value;
    }
    
    public boolean hasBreadcrumbItem(String expectedText) {
        return ui.elements(breadcrumbItems)
                .stream()
                .map(WebElement::getText)
                .anyMatch(text -> text != null && text.trim().equals(expectedText));
    }

    public OpenCartMyAccountPage goToSideMenuMyAccount() {
        ui.click(sideMenuMyAccountLink);
        return new OpenCartMyAccountPage(ui);
    }

    public OpenCartEditAccountPage goToSideMenuEditAccount() {
        ui.click(sideMenuEditAccountLink);
        return new OpenCartEditAccountPage(ui);
    }

    public OpenCartChangePasswordPage goToSideMenuPassword() {
        ui.click(sideMenuPasswordLink);
        return new OpenCartChangePasswordPage(ui);
    }

    public OpenCartLoginPage goToSideMenuLogout() {
        ui.click(sideMenuLogoutLink);
        return new OpenCartLoginPage(ui);
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
    
    public OpenCartEditAccountPage setDesktopViewport() {
        ui.setViewportSize(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        return this;
    }

    public OpenCartEditAccountPage setTabletViewport() {
        ui.setViewportSize(TABLET_WIDTH, TABLET_HEIGHT);
        return this;
    }

    public OpenCartEditAccountPage setMobileViewport() {
        ui.setViewportSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        return this;
    }

    public boolean isFormUsable() {
        return ui.isVisible(formCustomer)
                && ui.isVisible(firstNameInput)
                && ui.isVisible(lastNameInput)
                && ui.isVisible(emailInput)
                && ui.isVisible(continueButton);
    }
    
    public OpenCartEditAccountPage refresh() {
        DriverManager.getDriver().navigate().refresh();
        ui.waitDocumentReady();
        return this;
    }

    public void goForward() {
        DriverManager.getDriver().navigate().forward();
        ui.waitDocumentReady();
    }
}