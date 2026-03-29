package com.fahym.tas.observability.allure;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public final class AllureLabeler {
    private AllureLabeler() {}

    public static void applyLabels(Scenario scenario) {
        for (String rawTag : scenario.getSourceTagNames()) {
            String tag = normalize(rawTag);

            if (tag.equals("ui") || tag.equals("api")) {
                Allure.label("layer", tag);
            } else if (tag.equals("smoke") || tag.equals("regression")) {
                Allure.label("runType", tag);
            } else if (tag.startsWith("REQ-")) {
                Allure.label("requirement", tag);
            } else if (tag.startsWith("team-")) {
                Allure.label("owner", tag);
            }
        }

        Allure.label("framework", "tas-automation");
    }

    private static String normalize(String raw) {
        if (raw == null) return "";
        String t = raw.trim();
        if (t.startsWith("@")) t = t.substring(1);
        return t;
    }
}