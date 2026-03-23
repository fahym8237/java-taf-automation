package com.fahym.tas.data.generation;

import java.security.SecureRandom;

public final class Randoms {
    private Randoms() {}

    private static final String ALPHANUM = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RND = new SecureRandom();

    public static String alphanum(int len) {
        if (len <= 0) throw new IllegalArgumentException("len must be > 0");
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALPHANUM.charAt(RND.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}