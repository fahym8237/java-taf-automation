package com.fahym.tas.governance.qualitygates.gates;

import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.policy.TagPolicy;
import com.fahym.tas.governance.qualitygates.GateResult;
import com.fahym.tas.governance.qualitygates.QualityGate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequireLayerTagGate implements QualityGate {

    @Override
    public String name() {
        return "RequireLayerTagGate";
    }

    @Override
    public GateResult evaluate(Set<String> rawTags, GovernanceConfig cfg) {
        if (!cfg.failOnMissingLayer()) return GateResult.pass(name());

        long layerCount = rawTags.stream()
                .map(TagPolicy::normalize)
                .filter(t -> TagPolicy.allowedLayerTags().contains(t))
                .count();

        if (layerCount == 1) return GateResult.pass(name());

        List<String> v = new ArrayList<>();
        v.add("Scenario must have exactly one layer tag: @ui OR @api (not zero, not both).");
        return GateResult.fail(name(), v);
    }
}