package com.fahym.tas.observability.paths;

import java.nio.file.Path;

// All paths are centralized
public final class ReportPaths {
    private ReportPaths() {}

    public static Path targetDir() { return Path.of("target"); } // target/

    public static Path logsDir() { return targetDir().resolve("logs"); }  // target/logs/
    public static Path screenshotsDir() { return targetDir().resolve("screenshots"); } // target/screenshots/
    public static Path htmlDir() { return targetDir().resolve("html"); } // target/html/
    public static Path cucumberDir() { return targetDir().resolve("cucumber"); } // target/cucumber/
}
