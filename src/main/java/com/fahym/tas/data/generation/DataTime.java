package com.fahym.tas.data.generation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DataTime {
    private DataTime() {}

    public static String nowCompact() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}