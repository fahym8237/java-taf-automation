package com.fahym.tas.governance.qualitygates;

import com.fahym.tas.observability.attachments.AttachmentManager;
import com.fahym.tas.observability.run.RunInfo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class GateReporter {
    private GateReporter() {}

    private static final List<String> LINES = new ArrayList<>();

    public static void record(String scenarioName, String scenarioId, List<GateResult> results) {
        // store as JSON lines (simple and robust)
        String safeName = escape(scenarioName);
        String safeId = escape(scenarioId);

        for (GateResult r : results) {
            String json = "{"
                    + "\"runId\":\"" + RunInfo.runId() + "\","
                    + "\"scenarioId\":\"" + safeId + "\","
                    + "\"scenarioName\":\"" + safeName + "\","
                    + "\"gate\":\"" + escape(r.gateName()) + "\","
                    + "\"passed\":" + r.passed() + ","
                    + "\"violations\":" + toJsonArray(r.violations())
                    + "}";
            LINES.add(json);
        }
    }

    public static Path flushToDisk() {
        Path dir = Path.of("target", "governance", "qualitygates");
        AttachmentManager.ensureDir(dir);
        Path file = dir.resolve("gate-results-" + RunInfo.runId() + ".jsonl");
        String content = String.join(System.lineSeparator(), LINES);
        return AttachmentManager.saveText(dir, file.getFileName().toString(), content);
    }

    private static String toJsonArray(List<String> items) {
        if (items == null || items.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(escape(items.get(i))).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}