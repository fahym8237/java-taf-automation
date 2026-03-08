package com.fahym.tas.observability.run;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// one runId per JVM run
public final class RunInfo {
    private RunInfo() {}

    private static final String RUN_ID = createRunId();

    public static String runId() {
        return RUN_ID;
    }

    private static String createRunId() {
        String tf = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String shortId = UUID.randomUUID().toString().substring(0, 8);
        return tf + "_" + shortId;
    }
}
