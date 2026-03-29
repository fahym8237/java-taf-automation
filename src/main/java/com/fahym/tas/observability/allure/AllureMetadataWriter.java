package com.fahym.tas.observability.allure;

import com.fahym.tas.core.config.Config;
import com.fahym.tas.observability.attachments.AttachmentManager;

import java.nio.file.Path;

public final class AllureMetadataWriter {
    private AllureMetadataWriter() {}

    public static void writeEnvironment(Config cfg) {
        Path dir = Path.of("target", "allure-results");
        AttachmentManager.ensureDir(dir);

        String content = ""
                + "framework=TAS Automation" + System.lineSeparator()
                + "language=Java 24" + System.lineSeparator()
                + "runner=Cucumber JUnit Platform" + System.lineSeparator()
                + "env=" + cfg.env() + System.lineSeparator()
                + "browser=" + cfg.browser() + System.lineSeparator()
                + "headless=" + cfg.headless() + System.lineSeparator()
                + "remote.enabled=" + cfg.remoteEnabled() + System.lineSeparator()
                + "base.url=" + cfg.baseUrl() + System.lineSeparator()
                + "api.base.url=" + cfg.apiBaseUrl() + System.lineSeparator();

        AttachmentManager.saveText(dir, "environment.properties", content);
    }

    public static void writeExecutor() {
        Path dir = Path.of("target", "allure-results");
        AttachmentManager.ensureDir(dir);

        String buildName = System.getProperty("build.name",
                System.getenv().getOrDefault("GITHUB_RUN_NUMBER", "local"));
        String buildUrl = System.getProperty("build.url",
                System.getenv().getOrDefault("GITHUB_SERVER_URL", ""));

        String json = "{"
                + "\"name\":\"TAS Automation\","
                + "\"type\":\"local\","
                + "\"buildName\":\"" + esc(buildName) + "\","
                + "\"buildUrl\":\"" + esc(buildUrl) + "\""
                + "}";

        AttachmentManager.saveText(dir, "executor.json", json);
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    
    public static void copyCategoriesIfPresent() {
        Path dir = Path.of("target", "allure-results");
        AttachmentManager.ensureDir(dir);

        try (var is = Thread.currentThread().getContextClassLoader().getResourceAsStream("categories.json")) {
            if (is == null) return;
            String content = new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            AttachmentManager.saveText(dir, "categories.json", content);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to copy Allure categories.json", e);
        }
    }
}