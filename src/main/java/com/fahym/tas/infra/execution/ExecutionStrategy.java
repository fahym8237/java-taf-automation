package com.fahym.tas.infra.execution;

import java.net.URI;
import java.util.Map;

//encapsulates the chosen target, remote URL, and 
//   capabilities into a single object that the rest of the system can use.
public final class ExecutionStrategy {
    private final ExecutionTarget target;
    private final URI remoteUrl;
    private final Map<String, Object> capabilities;

    public ExecutionStrategy(ExecutionTarget target, URI remoteUrl, Map<String, Object> capabilities) {
        this.target = target;
        this.remoteUrl = remoteUrl;
        this.capabilities = capabilities == null ? Map.of() : Map.copyOf(capabilities);
    }

    public ExecutionTarget target() { return target; }
    public boolean isRemote() { return target.isRemote(); }
    public URI remoteUrl() { return remoteUrl; }
    public Map<String, Object> capabilities() { return capabilities; }
}
