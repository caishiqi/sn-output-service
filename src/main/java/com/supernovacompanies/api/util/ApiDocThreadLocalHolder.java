package com.supernovacompanies.api.util;

/**
 * @author damon
 * @date 01/31/2018
 */
public final class ApiDocThreadLocalHolder {

    private static final ThreadLocal<String> UNIVERSE = new ThreadLocal<>();

    private ApiDocThreadLocalHolder() {
    }

    public static void setUniverse(String userName) {
        UNIVERSE.set(userName);
    }

    public static String getUniverse() {
        return UNIVERSE.get();
    }


    public static void clear() {
        UNIVERSE.remove();
    }
}


