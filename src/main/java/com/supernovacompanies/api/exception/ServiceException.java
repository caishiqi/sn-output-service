package com.supernovacompanies.api.exception;

/**
 * @author chen
 * @date 01/31/2018
 */
public class ServiceException extends RuntimeException {

    private final ServiceErrorEnum serviceErrorEnum;

    public ServiceException(ServiceErrorEnum serviceErrorEnum, Throwable exception) {
        super(exception);
        this.serviceErrorEnum = serviceErrorEnum;
    }

    public ServiceException(ServiceErrorEnum serviceErrorEnum) {
        this.serviceErrorEnum = serviceErrorEnum;
    }

    public ServiceErrorEnum getServiceErrorEnum() {
        return serviceErrorEnum;
    }

}
