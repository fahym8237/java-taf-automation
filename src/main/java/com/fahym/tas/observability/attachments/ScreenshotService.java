package com.fahym.tas.observability.attachments;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.core.driver.selenium.SeleniumScreenshooter;
import com.fahym.tas.observability.paths.ReportPaths;
import com.fahym.tas.observability.run.RunInfo;

import java.nio.file.Path;

public final class ScreenshotService {
    private ScreenshotService() {}

    public static byte[] captureBytes() {
        return SeleniumScreenshooter.capturePng(DriverManager.getDriver());
    }

    public static Path captureToDisk(String scenarioId, String label) {
        byte[] png = captureBytes();
        Path dir = ReportPaths.screenshotsDir().resolve(RunInfo.runId());
        String file = scenarioId + "_" + safe(label) + ".png";
        return AttachmentManager.saveBytes(dir, file, png);
    }

    private static String safe(String s) {
        return s == null ? "screenshot" : s.replaceAll("[^a-zA-Z0-9._-]+", "_");
    }
}
