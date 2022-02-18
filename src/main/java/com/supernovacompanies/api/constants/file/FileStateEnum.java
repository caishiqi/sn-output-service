package com.supernovacompanies.api.constants.file;

/**
 * @author chen
 * @date 02/27/2018
 */
public enum FileStateEnum {

    /**
     * CREATED
     */
    CREATED(1),
    /**
     * UPLOADING
     */
    UPLOADING(2),
    /**
     * SCANNING
     */
    SCANNING(3),
    /**
     * STORING
     */
    STORING(4),
    /**
     * ERROR
     */
    ERROR(5),
    /**
     * READY
     */
    READY(6),
    /**
     * DELETED
     */
    DELETED(7),

    /**
     * virus
     */
    VIRUS(8);

    private Integer code;

    FileStateEnum(Integer code) {
        this.code = code;
    }

    public static FileStateEnum codeOf(Integer code) {
        for (FileStateEnum fileStateEnum : FileStateEnum.values()) {
            if (fileStateEnum.code.equals(code)) {
                return fileStateEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }
}
