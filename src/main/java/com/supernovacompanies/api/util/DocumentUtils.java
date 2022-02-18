package com.supernovacompanies.api.util;

/**
 * @author wangxian
 * @date 2017/10/24 下午2:45
 */
public final class DocumentUtils {
    public static final String TEMPLATE_FILE_SUFFIX = ".pdf";
    private static final String PAYLOAD_SUFFIX = ".request";
    private static final String DOCUMENT_INFO_SUFFIX = ".info";

    private DocumentUtils() {
    }

    public static String generatePDFName(String fileName) {
        return fileName + TEMPLATE_FILE_SUFFIX;
    }

    public static String generatePayloadName(String fileName) {
        return fileName + PAYLOAD_SUFFIX;
    }

    public static String generateInfoName(String fileName) {
        return fileName + DOCUMENT_INFO_SUFFIX;
    }

}
