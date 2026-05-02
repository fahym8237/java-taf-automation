package com.fahym.tas.domain.ui.pages.opencart.account;


import com.fahym.tas.domain.ui.pages.base.BasePage;
import com.fahym.tas.domain.ui.pages.base.Locators;
import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.OpenCartUrls;
import com.fahym.tas.domain.ui.pages.opencart.edit.OpenCartEditAccountPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;
import com.fahym.tas.domain.ui.pages.opencart.password.OpenCartChangePasswordPage;

import org.openqa.selenium.By;

public final class OpenCartMyAccountPage extends BasePage {

    private static final By pageHeader = Locators.xpath("//h1[normalize-space()='My Account']");
    private static final By logoutLink = Locators.xpath("(//a[@class='list-group-item'][normalize-space()='Logout'])[1]");
    private static final By changePasswordLink = Locators.xpath("//a[contains(@href,'account/password')]");
    private static final By editAccountLink = Locators.xpath("//a[contains(@href,'account/edit')]");
    
    private static OpenCartUrls urls = OpenCartUrls.getInstance();
    
    public OpenCartMyAccountPage(UiActions ui) {
        super(ui);
    }

    public OpenCartMyAccountPage open() {
        ui.open(urls.getaccountPageUrl());
        return this;
    }

    @Override
    public boolean isLoaded() {
        return ui.currentUrl().contains("route=account/account") || ui.isVisible(pageHeader);
    }

    public OpenCartLoginPage logout() {
        ui.click(logoutLink);
        return new OpenCartLoginPage(ui);
    }	
    

   public OpenCartChangePasswordPage goToChangePassword() {
     ui.click(changePasswordLink);
     return new OpenCartChangePasswordPage(ui);
    }

   public OpenCartEditAccountPage goToEditAccount() {
	    ui.click(editAccountLink);
	    return new OpenCartEditAccountPage(ui);
	}
   
   

		
}