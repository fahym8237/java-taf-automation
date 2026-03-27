package com.fahym.tas.governance.policy;

import java.util.Set;
import java.util.regex.Pattern;

public final class TagPolicy {

    private TagPolicy() {}

    private static final Pattern REQ = Pattern.compile("^REQ-\\d+$");

    public static Pattern requirementPattern() {
        return REQ;
    }

    public static Set<String> allowedLayerTags() {
        return Set.of("ui", "api");
    }

    public static Set<String> allowedRunTypeTags() {
        return Set.of("smoke", "regression");
    }

    public static boolean isRequirementTag(String rawTag) {
        String t = normalize(rawTag);
        return requirementPattern().matcher(t).matches();
    }

    public static String normalize(String rawTag) {
        if (rawTag == null) return "";
        String t = rawTag.trim();
        if (t.startsWith("@")) t = t.substring(1);
        return t;
    }
}