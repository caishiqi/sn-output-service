package com.supernovacompanies.api.exception;

import com.supernovacompanies.venus.result.IGlobalResult;
import org.springframework.http.HttpStatus;

/**
 * @author wystan Luo
 */
public enum ConfigurationErrorEnum implements IGlobalResult {

    /**
     * record not found
     */
    OBJECT_NOT_FOUND(157001, HttpStatus.NOT_FOUND, "configuration.object.not.found"),

    /**
     *
     */
    OBJECT_HAS_EXISTS(157002, HttpStatus.BAD_REQUEST, "configuration.object.exits"),

    MAIL_TEMPLATE_VARIABLE_HAS_EXISTS(157003, HttpStatus.BAD_REQUEST, "configuration.mail.template.variable.exists"),
    MAIL_TEMPLATE_HAS_EXISTS(157004, HttpStatus.BAD_REQUEST, "configuration.mail.template.exists"),
    MAIL_SENDER_NOT_FOUND(157005, HttpStatus.NOT_FOUND, "configuration.mail.sender.not.found"),
    MAIL_SCHEME_NOT_FOUND(157006, HttpStatus.NOT_FOUND, "configuration.mail.scheme.not.found");

    private int code;

    private HttpStatus httpStatus;

    private String key;

    ConfigurationErrorEnum(int code, HttpStatus httpStatus, String key) {
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
