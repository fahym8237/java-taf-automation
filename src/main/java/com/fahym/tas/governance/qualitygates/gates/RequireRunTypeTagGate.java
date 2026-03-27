package com.fahym.tas.governance.qualitygates.gates;

import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.policy.TagPolicy;
import com.fahym.tas.governance.qualitygates.GateResult;
import com.fahym.tas.governance.qualitygates.QualityGate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequireRunTypeTagGate implements QualityGate {

    @Override
    public String name() {
        return "RequireRunTypeTagGate";
    }

    @Override
    public GateResult evaluate(Set<String> rawTags, GovernanceConfig cfg) {
        if (!cfg.failOnMissingRunType()) return GateResult.pass(name());

        boolean hasRunType = rawTags.stream()
                .map(TagPolicy::normalize)
                .anyMatch(t -> TagPolicy.allowedRunTypeTags().contains(t));

        if (hasRunType) return GateResult.pass(name());

        List<String> v = new ArrayList<>();
        v.add("Scenario must have run type tag: @smoke or @regression.");
        return GateResult.fail(name(), v);
    }
}