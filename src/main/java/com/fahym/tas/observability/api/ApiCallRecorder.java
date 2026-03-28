package com.fahym.tas.observability.api;

public final class ApiCallRecorder {
    private ApiCallRecorder() {}

    private static final ThreadLocal<ApiExchange> LAST = new ThreadLocal<>();

    public static void record(ApiExchange ex) {
        LAST.set(ex);
    }

    public static ApiExchange last() {
        return LAST.get();
    }

    public static boolean hasLast() {
        return LAST.get() != null;
    }

    public static void clear() {
        LAST.remove();
    }
}