package com.supernovacompanies.api.util;

import java.security.SecureRandom;

/**
 * @author xian
 * @date 2018/02/02 上午9:59
 */
public final class EnvelopeUtils {

    public static final String HTTPS_PROTOCOL_LOWER_CASE = "https://";
    public static final String HTTPS_PROTOCOL_UPPER_CASE = "HTTPS://";


    private static final String[] LOWER_LETTER_NUM_CODE = new String[]{"3", "a", "b", "c", "d", "e", "f",
            "h", "7", "i", "j", "k", "6", "m", "n", "p", "4", "r", "s", "t", "u", "v", "8",
            "w", "x", "y", "5"};

    public static String getValidaCode(int codeNum) {
        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = codeNum; i > 0; i--) {
            int a = secureRandom.nextInt(LOWER_LETTER_NUM_CODE.length);
            code.append(LOWER_LETTER_NUM_CODE[a]);
        }
        return code.toString();
    }

    /**
     * add https protocol to address
     *
     * @param address
     * @return
     */
    public static String addHttpsProtocol(String address) {
        if (address.startsWith(HTTPS_PROTOCOL_LOWER_CASE) || address.startsWith(HTTPS_PROTOCOL_UPPER_CASE)) {
            return address;
        }
        return HTTPS_PROTOCOL_LOWER_CASE + address;
    }

    private EnvelopeUtils() {
    }

}
