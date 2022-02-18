package com.supernovacompanies.api.exception;

import com.supernovacompanies.venus.result.IGlobalResult;
import org.springframework.http.HttpStatus;

/**
 * @author xian
 * @date 2018/01/30 下午3:51
 */
public enum DocumentErrorCodeEnum implements IGlobalResult {

    /**
     * parameter input error
     */
    PARAMETER_INPUT_ERROR(109100, "params.error", HttpStatus.BAD_REQUEST),

    /**
     * template not found
     */
    TEMPLATE_NOT_FOUND(109102, "template.not.found", HttpStatus.BAD_REQUEST),

    /**
     * unknown error
     */
    UNKNOWN_ERROR(109103, "unknown.error", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * pdf engine error
     */
    PDF_ENGINE_ERROR(109104, "pdf.engine.error", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * resource not found
     */
    RESOURCE_NOT_FOUND(109105, "resource.not.found", HttpStatus.NOT_FOUND);

    private int code;
    private String key;
    private HttpStatus httpStatus;

    DocumentErrorCodeEnum(int code, String key, HttpStatus httpStatus) {
        this.code = code;
        this.key = key;
        this.httpStatus = httpStatus;
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
