package com.fahym.tas.governance.policy;

public final class GovernanceConfig {

    private final boolean failOnMissingReq;
    private final boolean failOnMissingLayer;
    private final boolean failOnMissingRunType;
    private final boolean forbidWipInCi;
    private final boolean ci;

    public GovernanceConfig(boolean ci,
                            boolean failOnMissingReq,
                            boolean failOnMissingLayer,
                            boolean failOnMissingRunType,
                            boolean forbidWipInCi) {
        this.ci = ci;
        this.failOnMissingReq = failOnMissingReq;
        this.failOnMissingLayer = failOnMissingLayer;
        this.failOnMissingRunType = failOnMissingRunType;
        this.forbidWipInCi = forbidWipInCi;
    }

    public static GovernanceConfig fromSystem() {
        // CI detection: GitHub Actions sets CI=true, many CI systems do too
        boolean ci = Boolean.parseBoolean(System.getProperty("ci", System.getenv().getOrDefault("CI", "false")));

        return new GovernanceConfig(
                ci,
                true,  // missing REQ fails
                true,  // missing @ui/@api fails
                true,  // missing @smoke/@regression fails
                true   // @wip forbidden in CI
        );
    }

    public boolean isCi() { return ci; }

    public boolean failOnMissingReq() { return failOnMissingReq; }
    public boolean failOnMissingLayer() { return failOnMissingLayer; }
    public boolean failOnMissingRunType() { return failOnMissingRunType; }
    public boolean forbidWipInCi() { return forbidWipInCi; }
}