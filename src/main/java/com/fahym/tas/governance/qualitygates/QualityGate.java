package com.fahym.tas.governance.qualitygates;

import com.fahym.tas.governance.policy.GovernanceConfig;

import java.util.Set;

public interface QualityGate {
    String name();
    GateResult evaluate(Set<String> rawTags, GovernanceConfig cfg);
}