package com.supernovacompanies.api.constants.file;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * @author chen
 * @date 02/26/2018
 */
public final class FileConstants {

    private FileConstants() {
    }

    public static final String TYPE_DEFAULT = "DEFAULT";

    public static final String S3_KEY_SEPARATION = "/";

    public static final String STATIC_RESOURCE_PATH = "static-resources";

    public static final String VIRUS_SCAN_STATUS_CHECK_KEY = "virusScan/selfInspect.txt";

    public static final int KEY_PART_NUM = 3;

    public static final String FILE_STORAGE_SYMBOL = "file-storage";

    private static final String REDIS_KEY_SEPARATOR = "_";

    public static final String REDIS_KEY_UPLOAD_PREFIX = "upload";

    public static final String REDIS_KEY_DOWNLOAD_PREFIX = "download";

    public static final String REDIS_KEY_DOWNLOAD_ZIP_PREFIX = "download-zip";

    public static String createRedisKey(String prefix, String uuid) {
        return StringUtils.joinWith(FileConstants.REDIS_KEY_SEPARATOR, FileConstants.FILE_STORAGE_SYMBOL, prefix, uuid);
    }

    public static final Map<String, Set<String>> allowedMediaType;

    static {
        allowedMediaType = new HashMap<>();
        allowedMediaType.put("application/pdf", new HashSet<>(Collections.singletonList("pdf")));
        allowedMediaType.put("application/postscript", new HashSet<>(Collections.singletonList("eps")));
        allowedMediaType.put("application/sql", new HashSet<>(Collections.singletonList("sql")));
        allowedMediaType.put("image/jpeg", new HashSet<>(Arrays.asList("jpg,jpeg,JPG".split(","))));
        allowedMediaType.put("image/png", new HashSet<>(Arrays.asList("png,PNG".split(","))));
        allowedMediaType.put("image/svg+xml", new HashSet<>(Collections.singletonList("svg")));
        allowedMediaType.put("text/csv", new HashSet<>(Collections.singletonList("csv")));
        allowedMediaType.put("text/plain", new HashSet<>(Collections.singletonList("txt")));
        allowedMediaType.put("pdf", new HashSet<>(Collections.singletonList("pdf")));
        allowedMediaType.put("application/msword", new HashSet<>(Collections.singletonList("doc")));
        allowedMediaType.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", new HashSet<>(Collections.singletonList("docx")));
        allowedMediaType.put("application/vnd.ms-excel", new HashSet<>(Collections.singletonList("xls")));
        allowedMediaType.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new HashSet<>(Collections.singletonList("xlsx")));
    }
}
