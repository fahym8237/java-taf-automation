package com.fahym.tas.governance.qualitygates;

import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.qualitygates.gates.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class GateRunner {

    private final List<QualityGate> gates;

    public GateRunner() {
        this.gates = List.of(
                new RequireRequirementIdGate(),
                new RequireLayerTagGate(),
                new RequireRunTypeTagGate(),
                new ForbidWipInCiGate()
        );
    }

    public List<GateResult> evaluate(Set<String> tags, GovernanceConfig cfg) {
        List<GateResult> results = new ArrayList<>();
        for (QualityGate gate : gates) {
            results.add(gate.evaluate(tags, cfg));
        }
        return results;
    }

    public static boolean allPassed(List<GateResult> results) {
        return results.stream().allMatch(GateResult::passed);
    }

    public static List<String> allViolations(List<GateResult> results) {
        List<String> v = new ArrayList<>();
        for (GateResult r : results) v.addAll(r.violations());
        return v;
    }
}