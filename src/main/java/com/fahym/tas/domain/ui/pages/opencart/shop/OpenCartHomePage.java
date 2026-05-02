package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;

import org.openqa.selenium.By;

public class OpenCartHomePage extends BasePage {

    private static final By logo = Locators.css("#logo");
    private static final By searchInput = Locators.name("search");
    private static final By searchButton = Locators.css("button.btn-light");
    private static final By cart = Locators.css("#header-cart");
    private static final By featuredSection = Locators.xpath("//h3[text()='Featured']");
    private static final By searchMenu = Locators.xpath("//div[@id='product-search']//li[2]");

    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    
   
    public OpenCartHomePage(UiActions ui) {
        super(ui);
    }

    public OpenCartHomePage open() {
        ui.open(urls.gethomePageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(logo) && ui.isVisible(searchInput);
    }

    public boolean isLogoVisible() { return ui.isVisible(logo); }
    public boolean isSearchBoxVisible() { return ui.isVisible(searchInput); }
    public boolean isCartVisible() { return ui.isVisible(cart); }
    public boolean isFeaturedSectionVisible() { return ui.isVisible(featuredSection); }

    public OpenCartSearchResultsPage search(String keyword) {
        ui.type(searchInput, keyword);
        ui.click(searchButton);
        ui.waitVisible(searchMenu);
        return new OpenCartSearchResultsPage(ui);
    }
}