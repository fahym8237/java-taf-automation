package com.fahym.tas.domain.api.booker.config;

public final class BookerPaths {
    private BookerPaths() {}

    public static final String PING = "/ping";
    public static final String AUTH = "/auth";
    public static final String BOOKING = "/booking";

    public static String bookingById(int id) {
        return BOOKING + "/" + id;
    }
}