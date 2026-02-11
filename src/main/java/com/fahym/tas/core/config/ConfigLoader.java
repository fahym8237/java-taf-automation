package com.fahym.tas.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//The engine that builds the Config object by merging configuration sources in a layered way.
public final class ConfigLoader {
    private ConfigLoader() {}

    public static Config load() {
        // 1) Base config
        Map<String, String> merged = new HashMap<>();
        merged.putAll(loadProps("config/application.properties"));

        // 2) Env override
        String env = System.getProperty(ConfigKeys.ENV, merged.getOrDefault(ConfigKeys.ENV, "local"));
        merged.put(ConfigKeys.ENV, env);
        merged.putAll(loadProps("config/env-" + env + ".properties"));

        // 3) System properties override (highest precedence)
        for (String key : merged.keySet().toArray(new String[0])) {
            String sys = System.getProperty(key);
            if (sys != null && !sys.isBlank()) merged.put(key, sys);
        }

        // Additionally support setting NEW keys purely via -D (not present in files yet)
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            String k = String.valueOf(e.getKey());
            String v = String.valueOf(e.getValue());
            if (k.startsWith("tas.") || k.equals(ConfigKeys.ENV) || k.equals(ConfigKeys.BASE_URL)
                    || k.equals(ConfigKeys.BROWSER) || k.equals(ConfigKeys.HEADLESS) || k.equals(ConfigKeys.TIMEOUTS_SECONDS)) {
                merged.put(k, v);
            }
        }

        validate(merged);
        return new Config(merged);
    }

    private static Map<String, String> loadProps(String classpathResource) {
        Properties p = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) return Map.of(); // optional file
            p.load(is);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load: " + classpathResource, ex);
        }
        Map<String, String> out = new HashMap<>();
        for (String name : p.stringPropertyNames()) {
            out.put(name, p.getProperty(name));
        }
        return out;
    }

    private static void validate(Map<String, String> m) {
        require(m, ConfigKeys.BASE_URL);
        // Defaults if absent
        m.putIfAbsent(ConfigKeys.BROWSER, "chrome");
        m.putIfAbsent(ConfigKeys.HEADLESS, "false");
        m.putIfAbsent(ConfigKeys.TIMEOUTS_SECONDS, "10");
    }

    private static void require(Map<String, String> m, String key) {
        String v = m.get(key);
        if (v == null || v.isBlank()) throw new IllegalStateException("Missing required config key: " + key);
    }
}
