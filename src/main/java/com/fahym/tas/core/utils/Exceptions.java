package com.fahym.tas.core.utils;


//All exception creation goes through a single place.
//Use later to add logging, enrich messages, or wrap exceptions.
public final class Exceptions {
    private Exceptions() {}

    public static IllegalStateException illegalState(String message) {
        return new IllegalStateException(message);
    }
}
