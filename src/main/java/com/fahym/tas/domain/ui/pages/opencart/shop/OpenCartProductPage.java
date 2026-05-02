package com.fahym.tas.domain.ui.pages.opencart.shop;

import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import org.openqa.selenium.By;

public class OpenCartProductPage extends BasePage {

    private static final By productTitle = Locators.css("#content h1");

    public OpenCartProductPage(UiActions ui) {
        super(ui);
    }

    @Override
    public boolean isLoaded() {
        return ui.isVisible(productTitle);
    }

    public String getProductTitle() {
        return ui.text(productTitle);
    }
}