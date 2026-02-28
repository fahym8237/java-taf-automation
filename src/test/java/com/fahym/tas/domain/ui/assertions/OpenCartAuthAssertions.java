package com.fahym.tas.domain.ui.assertions;

import com.fahym.tas.domain.ui.pages.opencart.forgotten.OpenCartForgottenPage;
import com.fahym.tas.domain.ui.pages.opencart.login.OpenCartLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public final class OpenCartAuthAssertions {

    public void assertLoginPageLoaded(OpenCartLoginPage page) {
        assertThat(page.isLoaded())
                .as("Login page should be loaded (form + Returning Customer header visible)")
                .isTrue();
        assertThat(page.returningCustomerHeaderText()).isEqualTo("Returning Customer");
    }

    public void assertForgottenPasswordPageLoaded(OpenCartForgottenPage page) {
        assertThat(page.isLoaded())
                .as("Forgotten password page should be loaded (header + form visible)")
                .isTrue();
        assertThat(page.headerText()).isEqualTo("Forgot Your Password?");
        // instruction text can change slightly; we only check it exists
        assertThat(page.hasInstructionText()).isTrue();
    }

    public void assertEmailValidationErrorVisible(OpenCartForgottenPage page) {
        assertThat(page.isEmailErrorVisible())
                .as("Email validation error should be visible")
                .isTrue();
    }
}