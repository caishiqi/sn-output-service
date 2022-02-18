package com.supernovacompanies.api.sequence.core.common;

import lombok.ToString;

/**
 * @author michael.he
 */
@ToString
public class Result {
    private String id;
    private Status status;

    public Result(String id, Status status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}