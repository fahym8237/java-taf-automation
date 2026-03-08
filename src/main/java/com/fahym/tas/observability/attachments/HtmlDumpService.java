package com.fahym.tas.observability.attachments;

import com.fahym.tas.core.driver.DriverManager;
import com.fahym.tas.observability.paths.ReportPaths;
import com.fahym.tas.observability.run.RunInfo;

import java.nio.file.Path;

public final class HtmlDumpService {
    private HtmlDumpService() {}

    public static Path dumpPageSource(String scenarioId, String label) {
        String html = DriverManager.getDriver().getPageSource();
        Path dir = ReportPaths.htmlDir().resolve(RunInfo.runId());
        String file = scenarioId + "_" + safe(label) + ".html";
        return AttachmentManager.saveText(dir, file, html);
    }

    private static String safe(String s) {
        return s == null ? "page" : s.replaceAll("[^a-zA-Z0-9._-]+", "_");
    }
}
