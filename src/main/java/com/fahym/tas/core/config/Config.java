package com.fahym.tas.core.config;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public final class Config {
    private final Map<String, String> values;

    public Config(Map<String, String> values) {
        this.values = Map.copyOf(Objects.requireNonNull(values, "values"));
    }

    public String get(String key) {
        String v = values.get(key);
        if (v == null) throw new IllegalStateException("Missing config key: " + key);
        return v;
    }

    //If the key exists,returns the associated value, if not  it returns the defaultValue you provide instead
    public String getOrDefault(String key, String defaultValue) {
        return values.getOrDefault(key, defaultValue);
    }
    
    //parses the string as a boolean.
    public boolean getBool(String key) {
        return Boolean.parseBoolean(get(key).trim());
    }
    
    //parses the string as an integer.
    public int getInt(String key) {
        return Integer.parseInt(get(key).trim());
    }

    //returns a Duration based on a config key.
    public Duration timeout() {
        return Duration.ofSeconds(getInt(ConfigKeys.TIMEOUTS_SECONDS));
    }

   
    public String env() {
        return getOrDefault(ConfigKeys.ENV, "local").trim();
    }

    public String baseUrl() {
        return get(ConfigKeys.BASE_URL).trim();
    }

    public String browser() {
        return getOrDefault(ConfigKeys.BROWSER, "chrome").trim().toLowerCase();
    }

    public boolean headless() {
        return Boolean.parseBoolean(getOrDefault(ConfigKeys.HEADLESS, "false").trim());
    }
}
