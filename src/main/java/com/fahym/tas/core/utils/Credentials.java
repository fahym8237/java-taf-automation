package com.fahym.tas.core.utils;

import java.security.SecureRandom;
import java.util.Random;

public class Credentials {
    // --- Singleton instance ---
    private static Credentials instance;

    // --- Fields ---
    private String loginPassword;
    private String loginEmail;
    private String loginNewEmail;
    private String loginNewPassword;
    private String loginOldPassword;

    // --- Private constructor ---
    private Credentials() {
        // Load initial values from environment variables
        this.loginPassword = System.getenv("LOGIN_PASSWORD");
        this.loginEmail = System.getenv("LOGIN_EMAIL");
        this.loginNewEmail = System.getenv("LOGIN_NEW_EMAIL");
        this.loginNewPassword = System.getenv("LOGIN_NEW_PASSWORD");
        this.loginOldPassword = System.getenv("LOGIN_OLD_PASSWORD");
    }

    // --- Singleton accessor ---
    public static Credentials getInstance() {
        if (instance == null) {
            instance = new Credentials();
        }
        return instance;
    }

    // --- Getters and setters ---
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginNewEmail() {
        return loginNewEmail;
    }

    public void setLoginNewEmail(String loginNewEmail) {
        this.loginNewEmail = loginNewEmail;
    }

    public String getLoginNewPassword() {
        return loginNewPassword;
    }

    public void setLoginNewPassword(String loginNewPassword) {
        this.loginNewPassword = loginNewPassword;
    }

    public String getLoginOldPassword() {
        return loginOldPassword;
    }

    public void setLoginOldPassword(String loginOldPassword) {
        this.loginOldPassword = loginOldPassword;
    }

    // --- Utility methods ---
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        String prefix = "Password";
        int number = 100 + random.nextInt(900);
        String specialChars = "!@#$%^&*";
        char special = specialChars.charAt(random.nextInt(specialChars.length()));
        return prefix + number + special;
    }

    public static String generateTestEmail() {
        Random random = new Random();
        int number = 100 + random.nextInt(900);
        return "testuser" + number + "@example.com";
    }
}
