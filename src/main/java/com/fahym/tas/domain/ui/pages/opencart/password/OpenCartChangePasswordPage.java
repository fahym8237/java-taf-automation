package com.fahym.tas.domain.ui.pages.opencart.password;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.account.OpenCartMyAccountPage;
import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.domain.ui.pages.opencart.edit.OpenCartEditAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import org.openqa.selenium.WebElement;
import java.util.Random;

import org.openqa.selenium.By;

public final class OpenCartChangePasswordPage extends BasePage {

    private static final By formPassword = Locators.id("form-password");
   
    private static final By pageHeader = Locators.xpath("//h1[normalize-space()='Change Password']");
    private static final By passwordInput = Locators.id("input-password");
    private static final By confirmInput = Locators.id("input-confirm");
    private static final By backButton = Locators.xpath("//a[contains(@href,'account/account') and normalize-space()='Back']");
    private static final By continueButton = Locators.xpath("//form[@id='form-password']//button[@type='submit' and normalize-space()='Continue']");

    private static final By passwordError = Locators.id("error-password");
    private static final By confirmError = Locators.id("error-confirm");
    private static final By successAlert = Locators.css(".alert-success");
    
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
    
    
    public OpenCartChangePasswordPage(UiActions ui) {
        super(ui);
    }

    public OpenCartChangePasswordPage open() {
        ui.open(urls.getchangePasswordPageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return  ui.isVisible(pageHeader);
    }

    public boolean isPasswordFieldVisible() {
        return ui.isVisible(passwordInput);
    }

    public boolean isConfirmFieldVisible() {
        return ui.isVisible(confirmInput);
    }

    public boolean isContinueButtonVisible() {
        return ui.isVisible(continueButton);
    }

    public boolean isBackButtonVisible() {
        return ui.isVisible(backButton);
    }

    public OpenCartChangePasswordPage enterPassword(String password) {
        ui.type(passwordInput, password);
        return this;
    }

    public OpenCartChangePasswordPage enterConfirm(String confirmPassword) {
        ui.type(confirmInput, confirmPassword);
        return this;
    }

    public OpenCartChangePasswordPage submitContinue() {
        ui.click(continueButton);
        return this;
    }

    public boolean isPasswordErrorVisible() {
        return ui.isVisible(passwordError) || ui.isVisibleNow(passwordError);
    }

    public boolean isConfirmErrorVisible() {
        return ui.isVisible(confirmError) || ui.isVisibleNow(confirmError);
    }

    public boolean isPasswordMasked() {
        return "password".equalsIgnoreCase(ui.attribute(passwordInput, "type"));
    }

    public boolean isConfirmMasked() {
        return "password".equalsIgnoreCase(ui.attribute(confirmInput, "type"));
    }

    public boolean isPasswordChangeSuccessful() {
        return ui.currentUrl().contains("route=account/account") || ui.isVisible(successAlert);
    }

    public OpenCartMyAccountPage goBackToMyAccount() {
        ui.click(backButton);
        return new OpenCartMyAccountPage(ui);
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
    
    public OpenCartChangePasswordPage setDesktopViewport() {
        ui.setViewportSize(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        return this;
    }

    public OpenCartChangePasswordPage setTabletViewport() {
        ui.setViewportSize(TABLET_WIDTH, TABLET_HEIGHT);
        return this;
    }

    public OpenCartChangePasswordPage setMobileViewport() {
        ui.setViewportSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        return this;
    }

    public boolean isFormUsable() {
        return ui.isVisible(formPassword)
                && ui.isVisible(passwordInput)
                && ui.isVisible(confirmInput)
                && ui.isVisible(continueButton);
    }
    
    
    public OpenCartChangePasswordPage refresh() {
        DriverManager.getDriver().navigate().refresh();
        ui.waitDocumentReady();
        return this;
    }

    public void goForward() {
        DriverManager.getDriver().navigate().forward();
        ui.waitDocumentReady();
    }

    public String passwordValue() {
        return ui.attribute(passwordInput, "value");
    }

    public String confirmValue() {
        return ui.attribute(confirmInput, "value");
    }
    
    public static String generatePassword() {
        Random random = new Random();
        
        // Fixed prefix
        String prefix = "Password";
        
        // Random number between 100 and 999
        int number = 100 + random.nextInt(900);
        
        // Special characters to choose from
        String specialChars = "!@#$%^&*";
        char special = specialChars.charAt(random.nextInt(specialChars.length()));
        
        // Final password
        return prefix + number + special;
    }
}