package com.supernovacompanies.api.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author chuang
 * @date 2019-12-05
 **/
public final class RandomStringUtils {

    /**
     * Assign a string that contains the set of characters you allow.
     */
    private static final String SYMBOLS = "qwertyuiopasdfghjklzxcvbnmABCDEFGJKLMNPRSTUVWXYZ0123456789";

    private static final Random RANDOM = new SecureRandom();

    public static String randomString(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(buf);
    }
}
