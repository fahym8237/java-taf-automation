package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;

import org.openqa.selenium.By;

public class OpenCartCheckoutPage extends BasePage {

    private static final By heading = Locators.xpath("//h1[normalize-space()='Checkout']");

    private static final By firstName = Locators.id("input-shipping-firstname");
    private static final By lastName = Locators.id("input-shipping-lastname");
    private static final By address = Locators.id("input-shipping-address-1");
    private static final By addressType = Locators.id("input-shipping-new");
    private static final By city = Locators.id("input-shipping-city");
    private static final By postcode = Locators.id("input-shipping-postcode");
    private static final By country = Locators.xpath("//select[@id='input-shipping-country']");
    private static final By region = Locators.xpath("//select[@id='input-shipping-zone']");
    
    private static final By firstNameError = Locators.xpath("//div[@id='error-shipping-firstname']");
    private static final By lastNameError = Locators.xpath("//div[@id='error-shipping-lastname']");
    private static final By addressError = Locators.xpath("(//div[@id='error-shipping-address-1'])[1]");
    private static final By cityError = Locators.xpath("//div[@id='error-shipping-city']");
    private static final By postcodeError = Locators.xpath("//div[@id='error-shipping-postcode']");
    private static final By regionError = Locators.xpath("//div[@id='error-shipping-zone']");

    private static final By continueBtn = Locators.id("button-shipping-address");
    private static final By errorAlert = Locators.css(".alert-danger");
    private static final By shoppingCartIsEmpty = Locators.xpath("//p[normalize-space()='Your shopping cart is empty!']");
    
    
    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    public OpenCartCheckoutPage(UiActions ui) {
        super(ui);
    }

    public OpenCartCheckoutPage open() {
        ui.open(urls.getcheckoutPageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(heading);
    }

    public String getHeading() {
        return ui.text(heading);
    }

    public boolean isShippingSectionVisible() {
        return ui.isVisible(firstName);
    }

    public boolean isFirstNameVisible() { return ui.isVisible(firstName); }
    public boolean isLastNameVisible() { return ui.isVisible(lastName); }
    public boolean isAddressVisible() { return ui.isVisible(address); }
    public boolean isCityVisible() { return ui.isVisible(city); }
    public boolean isPostcodeVisible() { return ui.isVisible(postcode); }
    public boolean isCountryVisible() { return ui.isVisible(country); }
    public boolean isRegionVisible() { return ui.isVisible(region); }

    public boolean isFirstNameErrorVisible() { return ui.isVisible(firstNameError); }
    public boolean isLastNameErrorVisible() { return ui.isVisible(lastNameError); }
    public boolean isAddressErrorVisible() { return ui.isVisible(addressError); }
    public boolean isCityErrorVisible() { return ui.isVisible(cityError); }
    public boolean isPostcodeErrorVisible() { return ui.isVisible(postcodeError); }
    public boolean isRegionErrorVisible() { return ui.isVisible(regionError); }
    
    
    public void selectNewAddress() {
    	
    	ui.clickRadio(addressType);
    }
    
    public void submitShipping() {
        ui.click(continueBtn);
    }

    public boolean hasValidationErrors() {
        return ui.isVisibleNow(errorAlert);
    }

    public void fillValidAddress() {
        ui.type(firstName, "John");
        ui.type(lastName, "Doe");
        ui.type(address, "123 Street");
        ui.type(city, "City");
        ui.type(postcode, "10000");
        ui.selectByValue(country, "222"); // e.g., United Kingdom
        ui.selectRegion(region, "Aberdeen");

        //ui.selectByIndex(region, 1);
    }
    
    public boolean shoppingCartIsEmpty() {
    	return ui.isVisible(shoppingCartIsEmpty);
    }

    public boolean isShippingAccepted() {
        return !hasValidationErrors();
    }

    public boolean isAuthRequired() {
        return ui.currentUrl().contains("account/login");
    }
}