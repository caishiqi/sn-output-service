package com.supernovacompanies.api.exception;

import com.supernovacompanies.venus.result.IGlobalResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chen
 * @date 03/09/2018
 */
@Getter
@Setter
public class ErrorResponseException extends RuntimeException {

    private IGlobalResult errorResponseEnum;

    private final Object error;

    public ErrorResponseException(IGlobalResult errorResponseEnum) {
        this.errorResponseEnum = errorResponseEnum;
        error = null;
    }

    public ErrorResponseException(IGlobalResult errorResponseEnum, Object error) {
        this.error = error;
        this.errorResponseEnum = errorResponseEnum;
    }

}
