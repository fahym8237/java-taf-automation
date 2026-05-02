package com.fahym.tas.data.generation;

import com.fahym.tas.observability.run.RunInfo;

public final class Unique {
    private Unique() {}

    public static String suffix() {
        return DataTime.nowCompact() + "_" + Randoms.alphanum(6);
    }

    public static String email(String prefix) {
        // unique + traceable (runId embedded)
        String run = RunInfo.runId();
        //String local = sanitize(prefix) + "+" + run + "_" + suffix();
        String local =  run ;
        return local + "@example.test";
    }

    @SuppressWarnings("unused")
	private static String sanitize(String s) {
        if (s == null || s.isBlank()) return "user";
        return s.trim().toLowerCase().replaceAll("[^a-z0-9]+", ".");
    }
}