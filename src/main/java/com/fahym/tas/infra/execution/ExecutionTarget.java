package com.fahym.tas.infra.execution;

public enum ExecutionTarget {
    LOCAL, GRID, CLOUD;

    public static ExecutionTarget from(String value) {
        if (value == null || value.isBlank()) return LOCAL;
        return switch (value.trim().toLowerCase()) {
            case "local" -> LOCAL;
            case "grid"  -> GRID;
            case "cloud" -> CLOUD;
            default -> throw new IllegalArgumentException("Unknown execution.target: " + value);
        };
    }

    public boolean isRemote() {
        return this == GRID || this == CLOUD;
    }
}
