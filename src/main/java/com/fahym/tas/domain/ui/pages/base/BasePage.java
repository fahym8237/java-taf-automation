package com.fahym.tas.domain.ui.pages.base;

public abstract class BasePage {

    protected final UiActions ui;

    protected BasePage(UiActions ui) {
        this.ui = ui;
    }

    /** page-specific load condition */
    public abstract boolean isLoaded();

    public void assertLoaded() {
        if (!isLoaded()) {
            throw new IllegalStateException("Page not loaded: " + getClass().getSimpleName());
        }
    }
}