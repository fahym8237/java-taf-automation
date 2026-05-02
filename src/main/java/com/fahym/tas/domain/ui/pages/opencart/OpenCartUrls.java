package com.fahym.tas.domain.ui.pages.opencart;


public  class OpenCartUrls {
	// --- Singleton instance ---
	public static OpenCartUrls instance;
	
	// --- Fields ---
	private String loginPageUrl;
	private String forgottenPageUrl;
	private String registerPageUrl;
	private String accountPageUrl;
	private String editAccountPageUrl;
	private String changePasswordPageUrl;
	private String logoutUrl;
	private String homePageUrl;
	private String checkoutPageUrl;
	
	 // --- Private constructor ---
    private OpenCartUrls() {
    	// Load initial values from environment variables
    	
    	this.loginPageUrl =	    	 System.getenv("LOGIN");
    	this.forgottenPageUrl = 	 System.getenv("FORGOTTEN");
    	this.registerPageUrl =		 System.getenv("REGISTER");
    	this. accountPageUrl =		 System.getenv("ACCOUNT");
    	this.editAccountPageUrl =	 System.getenv("EDIT_ACCOUNT");
    	this.changePasswordPageUrl = System.getenv("CHANGE_PASSWORD");
    	this.logoutUrl =			 System.getenv("LOGOUT");
    	this.homePageUrl =			 System.getenv("HOME_PAGE");
    	this.checkoutPageUrl=        System.getenv("CHECKOUT");
    	
    }

 // --- Singleton accessor ---
    public static OpenCartUrls getInstance() {
        if (instance == null) {
            instance = new OpenCartUrls();
        }
        return instance;
    }

    // --- Getters ---
    public String getloginPageUrl() {return loginPageUrl;}
    public String getforgottenPageUrl() {return forgottenPageUrl;}
    public String getregisterPageUrl() {return registerPageUrl;}
    public String getaccountPageUrl() {return accountPageUrl;}
    public String geteditAccountPageUrl() {return editAccountPageUrl;}
    public String getchangePasswordPageUrl() {return changePasswordPageUrl;}
    public String getlogoutUrl() {return logoutUrl;}
    public String gethomePageUrl() {return homePageUrl;}
    public String getcheckoutPageUrl() {return checkoutPageUrl;}
    
    


}