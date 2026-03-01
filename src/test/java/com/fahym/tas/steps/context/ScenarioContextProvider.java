package com.fahym.tas.steps.context;

public final class ScenarioContextProvider {
    private ScenarioContextProvider() {}

    private static final ThreadLocal<ScenarioContext> CTX = new ThreadLocal<>();

    public static void init() {
        CTX.set(new ScenarioContext());
    }

    public static ScenarioContext current() {
        ScenarioContext c = CTX.get();
        if (c == null) throw new IllegalStateException("ScenarioContext not initialized.");
        return c;
    }

    public static void dispose() {
        ScenarioContext c = CTX.get();
        if (c != null) c.clear();
        CTX.remove();
    }
}
