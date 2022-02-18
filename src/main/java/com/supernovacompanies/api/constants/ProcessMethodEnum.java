package com.supernovacompanies.api.constants;

/**
 * @Author: peter.pan
 * @Date: 2020/5/14 11:51
 * @Description: 
 */
public enum ProcessMethodEnum {
    ASYN(0),
    SYN(1);
    
    /**
     * code
     */
    private Integer code;

    ProcessMethodEnum(Integer code) {
        this.code = code;
    }

    public static ProcessMethodEnum valueOf(Integer code) {
        for (ProcessMethodEnum processMethodEnum : values()) {
            if (processMethodEnum.code.equals(code)) {
                return processMethodEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    public Integer getCode() {
        return code;
    }
}
