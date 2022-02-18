package com.supernovacompanies.api.exception;

import com.supernovacompanies.venus.result.IGlobalResult;
import org.springframework.http.HttpStatus;

/**
 * @author jack.he
 */
public enum RecordErrorEnum implements IGlobalResult {

    /**
     * server error
     */
    SERVER_ERROR(151000, HttpStatus.INTERNAL_SERVER_ERROR, "server.error"),

    /**
     * params error
     */
    PARAMS_ERROR(151001, HttpStatus.BAD_REQUEST, "param.error"),

    /**
     * obt id type error
     */
    OBT_ID_TYPE_ERROR(151002, HttpStatus.INTERNAL_SERVER_ERROR, "obj_id.and.type.error"),

    /**
     * message status error
     */
    MESSAGE_STATUS_ERROR(151003, HttpStatus.INTERNAL_SERVER_ERROR, "message.status.wrong"),

    /**
     * record id not exist
     */
    RECORD_ID_NOT_EXIST(151004, HttpStatus.BAD_REQUEST, "recordId.not.exist"),

    /**
     * record not found
     */
    RECORD_NOT_FOUND(151020, HttpStatus.NOT_FOUND, "record.not.found"),

    /**
     * record detail not found
     */
    RECORD_DETAIL_NOT_FOUND(151021, HttpStatus.INTERNAL_SERVER_ERROR, "record.detail.not.found"),

    /**
     * record obj not found
     */
    RECORD_OBJ_NOT_FOUND(151022, HttpStatus.INTERNAL_SERVER_ERROR, "record.obj.not.found"),

    /**
     * record activity not found
     */
    RECORD_ACTIVITY_NOT_FOUND(151023, HttpStatus.INTERNAL_SERVER_ERROR, "record.activity.not.found"),

    /**
     * document detail not found
     */
    DOCUMENT_DETAIL_NOT_FOUND(151024, HttpStatus.INTERNAL_SERVER_ERROR, "record.document.not.found"),

    /**
     * record flag not found
     */
    RECORD_FLAG_NOT_FOUND(151025, HttpStatus.INTERNAL_SERVER_ERROR, "record.flag.not.found"),

    /**
     * status table status is null
     */
    STATUS_TABLE_STATUIS_NOT_NULL(151026, HttpStatus.INTERNAL_SERVER_ERROR, "status.table.status.is.null"),

    /**
     * status table status length out of range
     */
    STATUS_TABLE_STATUIS_LENGTH_RANGE(151027, HttpStatus.INTERNAL_SERVER_ERROR, "status.table.name.length.range"),


    /**
     * status table status length out of range
     */
    RECORD_CAN_NOT_BE_DELETED_DUE_TO_CHILD_RECORDS(151028, HttpStatus.BAD_REQUEST, "record.can.not.delete.due.to.child.records");

    private int code;

    private HttpStatus httpStatus;

    private String key;

    RecordErrorEnum(int code, HttpStatus httpStatus, String key) {
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
