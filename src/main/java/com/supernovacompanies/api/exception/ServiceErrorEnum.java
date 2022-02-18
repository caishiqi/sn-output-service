package com.supernovacompanies.api.exception;

import com.supernovacompanies.venus.result.IGlobalResult;
import org.springframework.http.HttpStatus;

/**
 * EsErrorCodeEnum
 *
 * @author xian
 * @date 2017/12/12
 */
public enum ServiceErrorEnum implements IGlobalResult {

    /**
     * Internal error
     */
    INTERNAL_ERROR(107001, HttpStatus.INTERNAL_SERVER_ERROR, "service.internal.error"),
    RESOURCE_NOT_FOUND(107002, HttpStatus.NOT_FOUND, "service.resource.not.found"),
    PARAMS_ERROR(107003, HttpStatus.BAD_REQUEST, "service.param.error"),
    FILE_UPLOAD_ERROR(107004, HttpStatus.INTERNAL_SERVER_ERROR, "file.upload.error"),
    FILE_DOWNLOAD_ERROR(107005, HttpStatus.INTERNAL_SERVER_ERROR, "file.download.error"),
    TEMPLATE_FILE_NOTE_EXIST(107006, HttpStatus.BAD_REQUEST, "template.file.not.exist"),
    ATTACHMENT_PARAM_ERROR(107007, HttpStatus.BAD_REQUEST, "attachment.resourceId.and.fileData.null"),
    EMAIL_FORMAT_ERROR(107008, HttpStatus.BAD_REQUEST, "email.format.error"),

    GENERAL_ERROR(109001, HttpStatus.INTERNAL_SERVER_ERROR, "sms.internal.error"),
    TEMPLATE_NOT_FOUND(109002, HttpStatus.INTERNAL_SERVER_ERROR, "template.not.found"),
    INVALID_REQUEST_CONTENT(109003, HttpStatus.BAD_REQUEST, "invalid.request.content"),
    TEMPLATE_PROCESS_ERROR(109004, HttpStatus.INTERNAL_SERVER_ERROR, "template.process.error"),
    GENERATE_MESSAGE_ERROR(109005, HttpStatus.INTERNAL_SERVER_ERROR, "generate.message.error"),
    SENDING_SMS_ERROR(109006, HttpStatus.INTERNAL_SERVER_ERROR, "sending.sms.error"),
    SENDING_SES_ERROR(109007, HttpStatus.INTERNAL_SERVER_ERROR, "sending.ses.error"),
    SAVE_SMS_ERROR(109008, HttpStatus.INTERNAL_SERVER_ERROR, "save.sms.error"),
    PARSE_JSON_ERROR(109009, HttpStatus.INTERNAL_SERVER_ERROR, "parse.json.error"),
    TEMPLATE_READ_ERROR(109010, HttpStatus.INTERNAL_SERVER_ERROR, "template.read.error"),
    NUMBER_FORMAT_ERROR(109011, HttpStatus.INTERNAL_SERVER_ERROR, "number.format.error"),
    CENTER_CONFIG_ERROR(109012, HttpStatus.INTERNAL_SERVER_ERROR, "center.config.error"),
    CAN_NOT_FIND_USER_BY_EMAIL(109013, HttpStatus.NOT_FOUND, "can.not.find.user.by.email"),
    CAN_NOT_FIND_EMAIL_TEMPLATE(109014, HttpStatus.NOT_FOUND, "can.not.find.email.template"),
    SMS_VOICE_DAILY_LIMIT(109015, HttpStatus.BAD_REQUEST, "Only 5 voice messages that can be sent to a single recipient during a 24-hour period"),
    SMS_VOICE_MINUTES_LIMIT(109016, HttpStatus.BAD_REQUEST, "Only 20 voice messages that can be sent per minute"),
    SMS_VOICE_ERROR(109017, HttpStatus.BAD_REQUEST, "AWS SMS voice error"),
    CAN_NOT_FIND_10DLC_BY_WEBSITE(109018, HttpStatus.INTERNAL_SERVER_ERROR, "can.not.find.10DLC.by.website");

    private int code;
    private HttpStatus httpStatus;
    private String key;

    ServiceErrorEnum(int code, HttpStatus httpStatus, String key) {
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
