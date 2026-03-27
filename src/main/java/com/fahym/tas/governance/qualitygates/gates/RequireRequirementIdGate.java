package com.fahym.tas.governance.qualitygates.gates;

import com.fahym.tas.governance.policy.GovernanceConfig;
import com.fahym.tas.governance.policy.TagPolicy;
import com.fahym.tas.governance.qualitygates.GateResult;
import com.fahym.tas.governance.qualitygates.QualityGate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequireRequirementIdGate implements QualityGate {

    @Override
    public String name() {
        return "RequireRequirementIdGate";
    }

    @Override
    public GateResult evaluate(Set<String> rawTags, GovernanceConfig cfg) {
        if (!cfg.failOnMissingReq()) return GateResult.pass(name());

        boolean hasReq = rawTags.stream().anyMatch(TagPolicy::isRequirementTag);
        if (hasReq) return GateResult.pass(name());

        List<String> v = new ArrayList<>();
        v.add("Missing requirement tag. Add @REQ-<digits> (e.g., @REQ-1001).");
        return GateResult.fail(name(), v);
    }
}