package com.fahym.tas.integrations.xray;

public final class XrayExecutionInfoBuilder {
    private XrayExecutionInfoBuilder() {}

    public static String build(XrayConfig cfg) {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"fields\":{");
        json.append("\"project\":{\"key\":\"").append(esc(cfg.projectKey())).append("\"},");
        json.append("\"summary\":\"").append(esc(cfg.executionSummary())).append("\",");
        json.append("\"issuetype\":{\"name\":\"Test Execution\"},");
        json.append("\"description\":\"").append(esc(cfg.executionDescription())).append("\"");

        if (cfg.fixVersions() != null && !cfg.fixVersions().isBlank()) {
            json.append(",\"fixVersions\":[");
            String[] versions = cfg.fixVersions().split(",");
            for (int i = 0; i < versions.length; i++) {
                if (i > 0) json.append(",");
                json.append("{\"name\":\"").append(esc(versions[i].trim())).append("\"}");
            }
            json.append("]");
        }

        json.append("}");

        if (cfg.testPlanKey() != null && !cfg.testPlanKey().isBlank()) {
            json.append(",\"xrayFields\":{");
            json.append("\"testPlanKey\":\"").append(esc(cfg.testPlanKey())).append("\"");
            json.append("}");
        }

        json.append("}");
        return json.toString();
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}