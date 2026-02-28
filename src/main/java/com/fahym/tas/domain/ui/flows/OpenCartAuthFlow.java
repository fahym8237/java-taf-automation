package com.fahym.tas.domain.ui.flows;

import java.time.Duration;

import com.fahym.tas.domain.ui.pages.base.UiActions;
import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;

public final class OpenCartAuthFlow {

    private final UiActions ui;
    private final OpenCartLoginPage loginPage;

    public OpenCartAuthFlow(Duration timeout) {
        this.ui = new UiActions(timeout);
        this.loginPage = new OpenCartLoginPage(ui);
    }

    public OpenCartLoginPage openLogin() {
        return loginPage.open();
    }

    public OpenCartForgottenPage openForgottenViaLogin() {
        loginPage.open();
        loginPage.assertLoaded();
        return loginPage.goToForgottenPassword();
    }
}