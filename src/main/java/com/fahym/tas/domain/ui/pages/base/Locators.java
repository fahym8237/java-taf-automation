package com.fahym.tas.domain.ui.pages.base;

import org.openqa.selenium.By;

public final class Locators {
    private Locators() {}

    /* ---------------- Standard Locators ---------------- */

    public static By id(String id) {
        return By.id(id);
    }

    public static By name(String name) {
        return By.name(name);
    }

    public static By className(String className) {
        return By.className(className);
    }

    public static By tagName(String tagName) {
        return By.tagName(tagName);
    }

    public static By linkText(String text) {
        return By.linkText(text);
    }

    public static By partialLinkText(String text) {
        return By.partialLinkText(text);
    }

    public static By css(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

    public static By xpath(String xpathExpression) {
        return By.xpath(xpathExpression);
    }
    
}