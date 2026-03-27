package com.fahym.tas.governance.qualitygates.gates;

import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.policy.TagPolicy;
import com.fahym.tas.governance.qualitygates.GateResult;
import com.fahym.tas.governance.qualitygates.QualityGate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ForbidWipInCiGate implements QualityGate {

    @Override
    public String name() {
        return "ForbidWipInCiGate";
    }

    @Override
    public GateResult evaluate(Set<String> rawTags, GovernanceConfig cfg) {
        if (!cfg.forbidWipInCi()) return GateResult.pass(name());
        if (!cfg.isCi()) return GateResult.pass(name());

        boolean hasWip = rawTags.stream().map(TagPolicy::normalize).anyMatch(t -> t.equals("wip"));
        if (!hasWip) return GateResult.pass(name());

        List<String> v = new ArrayList<>();
        v.add("@wip is forbidden in CI. Remove @wip or exclude it from tag filter.");
        return GateResult.fail(name(), v);
    }
}