package com.haqi.csc577groupproject.model;

public class FailLogin {
    private String status;
    private Error error;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Error getError()   { return error; }
    public void setError(Error error) { this.error = error; }
}
