package com.fahym.tas.governance.qualitygates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GateResult {
    private final String gateName;
    private final boolean passed;
    private final List<String> violations;
    private final Map<String, Object> metadata;

    private GateResult(String gateName, boolean passed, List<String> violations, Map<String, Object> metadata) {
        this.gateName = gateName;
        this.passed = passed;
        this.violations = violations == null ? List.of() : List.copyOf(violations);
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static GateResult pass(String name) {
        return new GateResult(name, true, List.of(), Map.of());
    }

    public static GateResult fail(String name, List<String> violations) {
        return new GateResult(name, false, violations == null ? new ArrayList<>() : violations, Map.of());
    }

    public String gateName() { return gateName; }
    public boolean passed() { return passed; }
    public List<String> violations() { return violations; }
    public Map<String, Object> metadata() { return metadata; }
}