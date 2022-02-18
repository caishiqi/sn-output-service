package com.supernovacompanies.api.constants.file;

/**
 * @author chen
 * @date 02/28/2018
 */
public enum FileErrorEnum {
    /**
     * virus
     */
    VIRUS_ERROR("10"),
    /**
     * INTERNAL_ERROR
     */
    INTERNAL_ERROR("500"),

    /**
     * CACTUS_ERROR
     */
    CACTUS_ERROR("20"),
    /**
     * NOTICE_ERROR
     */
    NOTICE_ERROR("30"),
    /**
     * S3_ERROR
     */
    S3_ERROR("40");


    private String errorCode;

    FileErrorEnum(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
