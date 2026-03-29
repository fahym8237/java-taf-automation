package com.fahym.tas.integrations.xray;

import java.nio.file.Path;

public final class XrayConfig {

    private final String clientId;
    private final String clientSecret;
    private final String projectKey;
    private final String executionSummary;
    private final String executionDescription;
    private final String testPlanKey;
    private final String fixVersions;
    private final Path resultsFile;

    private XrayConfig(String clientId,
                       String clientSecret,
                       String projectKey,
                       String executionSummary,
                       String executionDescription,
                       String testPlanKey,
                       String fixVersions,
                       Path resultsFile) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.projectKey = projectKey;
        this.executionSummary = executionSummary;
        this.executionDescription = executionDescription;
        this.testPlanKey = testPlanKey;
        this.fixVersions = fixVersions;
        this.resultsFile = resultsFile;
    }

    public static XrayConfig fromEnv(String[] args) {
        Path results = args.length > 0
                ? Path.of(args[0])
                : Path.of("target", "cucumber", "cucumber.json");

        return new XrayConfig(
                requireEnv("XRAY_CLIENT_ID"),
                requireEnv("XRAY_CLIENT_SECRET"),
                requireEnv("XRAY_PROJECT_KEY"),
                envOrDefault("XRAY_EXECUTION_SUMMARY", "TAS Automation Execution"),
                envOrDefault("XRAY_EXECUTION_DESCRIPTION", "Execution imported from TAS"),
                System.getenv("XRAY_TEST_PLAN_KEY"),
                System.getenv("XRAY_FIX_VERSIONS"),
                results
        );
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + name);
        }
        return value;
    }

    private static String envOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public String clientId() { return clientId; }
    public String clientSecret() { return clientSecret; }
    public String projectKey() { return projectKey; }
    public String executionSummary() { return executionSummary; }
    public String executionDescription() { return executionDescription; }
    public String testPlanKey() { return testPlanKey; }
    public String fixVersions() { return fixVersions; }
    public Path resultsFile() { return resultsFile; }
}