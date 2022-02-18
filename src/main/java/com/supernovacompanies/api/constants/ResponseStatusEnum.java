package com.supernovacompanies.api.constants;

/**
 * @author lee
 */
public enum ResponseStatusEnum {

    PROCESSING(1),
    READY(0),
    FAILED(2);

    /**
     * code
     */
    private Integer code;

    ResponseStatusEnum(Integer code) {
        this.code = code;
    }

    public static ResponseStatusEnum valueOf(Integer code) {
        for (ResponseStatusEnum responseStatusEnum : values()) {
            if (responseStatusEnum.code.equals(code)) {
                return responseStatusEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    public Integer getCode() {
        return code;
    }
}
