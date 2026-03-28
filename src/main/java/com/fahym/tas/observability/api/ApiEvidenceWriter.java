package com.fahym.tas.observability.api;

import com.fahym.tas.observability.attachments.AttachmentManager;
import com.fahym.tas.observability.run.RunInfo;

import java.nio.file.Path;

public final class ApiEvidenceWriter {
    private ApiEvidenceWriter() {}

    public static Path writeLastExchange(String scenarioId, ApiExchange ex) {
        Path dir = Path.of("target", "api", RunInfo.runId());
        AttachmentManager.ensureDir(dir);

        String base = scenarioId.replaceAll("[^a-zA-Z0-9._-]+", "_");

        String reqJson = toRequestJson(ex);
        String resJson = toResponseJson(ex);

        AttachmentManager.saveText(dir, base + "_last-request.json", reqJson);
        return AttachmentManager.saveText(dir, base + "_last-response.json", resJson);
    }

    public static String toReadableSummary(ApiExchange ex) {
        StringBuilder sb = new StringBuilder();
        sb.append("API LAST EXCHANGE").append(System.lineSeparator());
        sb.append("Request: ").append(ex.method()).append(" ").append(ex.uri()).append(System.lineSeparator());
        sb.append("Status: ").append(ex.statusCode()).append(System.lineSeparator());

        if (ex.requestBody() != null && !ex.requestBody().isBlank()) {
            sb.append("--- Request Body ---").append(System.lineSeparator());
            sb.append(truncate(ex.requestBody(), 1500)).append(System.lineSeparator());
        }

        if (ex.responseBody() != null && !ex.responseBody().isBlank()) {
            sb.append("--- Response Body ---").append(System.lineSeparator());
            sb.append(truncate(ex.responseBody(), 2000)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, max) + System.lineSeparator() + "...(truncated)";
    }

    private static String toRequestJson(ApiExchange ex) {
        return "{"
                + "\"method\":\"" + esc(ex.method()) + "\","
                + "\"uri\":\"" + esc(ex.uri()) + "\","
                + "\"headers\":" + mapToJson(ex.requestHeaders()) + ","
                + "\"body\":\"" + esc(ex.requestBody()) + "\""
                + "}";
    }

    private static String toResponseJson(ApiExchange ex) {
        return "{"
                + "\"statusCode\":" + ex.statusCode() + ","
                + "\"headers\":" + mapToJson(ex.responseHeaders()) + ","
                + "\"body\":\"" + esc(ex.responseBody()) + "\""
                + "}";
    }

    private static String mapToJson(java.util.Map<String, String> map) {
        if (map == null || map.isEmpty()) return "{}";
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (var e : map.entrySet()) {
            if (i++ > 0) sb.append(",");
            sb.append("\"").append(esc(e.getKey())).append("\":");
            sb.append("\"").append(esc(e.getValue())).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "").replace("\n", "\\n");
    }
}