package com.fahym.tas.core.config;

public final class ConfigKeys {
    private ConfigKeys() {}

    // Automation Core Layer Imp
    public static final String ENV = "env";
    public static final String BASE_URL = "base.url";

    public static final String BROWSER = "browser";
    public static final String HEADLESS = "headless";

    public static final String TIMEOUTS_SECONDS = "timeouts.seconds";
    
    // Infrastructure Abstraction Layer Imp
    public static final String REMOTE_ENABLED = "remote.enabled";
    public static final String REMOTE_URL = "remote.url";
    public static final String EXECUTION_TARGET = "execution.target"; // optional override: local|grid|cloud
}
