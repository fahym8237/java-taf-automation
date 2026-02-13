package com.fahym.tas.infra.execution;

import com.fahym.tas.core.config.Config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public final class ExecutionResolver {
    public ExecutionStrategy resolve(Config cfg) {
        // Allow explicit override via execution.target
        ExecutionTarget target = ExecutionTarget.from(cfg.executionTargetOverride());

        // If no override, derive from remote.enabled
        if (cfg.executionTargetOverride().isBlank()) {
            target = cfg.remoteEnabled() ? ExecutionTarget.GRID : ExecutionTarget.LOCAL;
        }

        URI remoteUrl = null;
        if (target.isRemote()) remoteUrl = URI.create(cfg.remoteUrl());

        Map<String, Object> caps = new HashMap<>();
        // keep empty for pilot; later Layer E will enrich caps via policy (platform, version, vendor labels, etc.)

        return new ExecutionStrategy(target, remoteUrl, caps);
    }
}
