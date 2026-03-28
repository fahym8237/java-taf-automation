package com.fahym.tas.data.api.booker;

import com.fahym.tas.data.generation.Randoms;

import java.util.Map;

public final class BookingPatchFactory {
    private BookingPatchFactory() {}

    /** PATCH body: change firstname */
    public static Map<String, Object> patchFirstname() {
        return Map.of("firstname", "Patched_" + Randoms.alphanum(6));
    }

    /** PATCH body: change lastname */
    public static Map<String, Object> patchLastname() {
        return Map.of("lastname", "PatchedLast_" + Randoms.alphanum(6));
    }

    /** PATCH body: change additionalneeds */
    public static Map<String, Object> patchAdditionalNeeds() {
        return Map.of("additionalneeds", "Dinner_" + Randoms.alphanum(4));
    }
}