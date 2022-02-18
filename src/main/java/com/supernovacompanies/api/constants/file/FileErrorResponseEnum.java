package com.supernovacompanies.api.constants.file;

import com.supernovacompanies.venus.result.IGlobalResult;
import org.springframework.http.HttpStatus;

/**
 * @author chen
 * @date 02/28/2018
 */
public enum FileErrorResponseEnum implements IGlobalResult {

    /**
     * File Type Not Exist
     */
    FILE_TYPE_NOT_EXIST(106001, HttpStatus.BAD_REQUEST, "file.type.not.exist"),
    /**
     * Missing Necessary Param
     */
    MISSING_PARAM(106002, HttpStatus.BAD_REQUEST, "missing.param"),
    /**
     * Can Not Change RequireVirusScan True To False
     */
    REQUIRES_VIRUS_SCAN_ERROR(106003, HttpStatus.BAD_REQUEST, "requires.virus.scan.change.error"),
    /**
     * Can Not Change RequireVirusScan True To False
     */
    REQUIRES_VERSIONING_ERROR(106004, HttpStatus.BAD_REQUEST, "requires.versioning.change.error"),
    /**
     * Resource Not Found
     */
    RESOURCE_NOT_EXIST(106005, HttpStatus.NOT_FOUND, "not.found"),
//    /**
//     * File State Conflict
//     */
//    FILE_STATE_CONFLICT(106006, HttpStatus.CONFLICT, "file.state.conflict"),

    /**
     * File Upload State Conflict
     */
    FILE_UPLOAD_STATUS_ERROR(106006, HttpStatus.CONFLICT, "file.upload.status.error"),
    /**
     * Notice Type Not Exist
     */
    NOTICE_TYPE_NOT_EXIST(106007, HttpStatus.BAD_REQUEST, "notice.type.not.exist"),

    /**
     * Cactus Server Error
     */
    CACTUS_SERVER_ERROR(106008, HttpStatus.INTERNAL_SERVER_ERROR, "cactus.server.error"),
    /**
     * S3 Error
     */
    S3_ERROR(106009, HttpStatus.INTERNAL_SERVER_ERROR, "s3.error"),
    /**
     * INTERNAL_ERROR
     */
    INTERNAL_ERROR(106010, HttpStatus.INTERNAL_SERVER_ERROR, "internal.error"),

    /**
     * Param Error
     */
    PARAM_ERROR(106011, HttpStatus.BAD_REQUEST, "file.param.error"),

    /**
     * HEALTH_CHECK WHEN S3_FILE CONTENT_ERROR
     */
    HEALTH_CHECK_S3_FILECONTENT_ERROR(106040, HttpStatus.INTERNAL_SERVER_ERROR, "file.content.error"),

    /**
     * batch download Error
     */
    BATCH_DOWNLOAD_ERROR(106012, HttpStatus.INTERNAL_SERVER_ERROR, "batch.download.error"),

    /**
     * File download State Conflict
     */
    FILE_DOWNLOAD_STATUS_ERROR(106013, HttpStatus.CONFLICT, "file.download.status.error"),

    /**
     * Unsupported file media type or file extension
     */
    FILE_MEDIA_TYPE_NOT_SUPPORT(106014, HttpStatus.BAD_REQUEST, "file.media.type.not.support");


    private int code;
    private HttpStatus httpStatus;
    private String key;

    FileErrorResponseEnum(int code, HttpStatus httpStatus, String key) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.key = key;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
