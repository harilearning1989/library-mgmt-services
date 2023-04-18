package com.lib.mgmt.exceptions;

public class ErrorResponse {

    private String message;
    private int status;
    public ErrorResponse() {
        super();
    }
    public ErrorResponse(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorResponse [status=" + status + ", message=" + message + "]";
    }
}
