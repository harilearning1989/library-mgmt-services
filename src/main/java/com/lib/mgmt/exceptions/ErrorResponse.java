package com.lib.mgmt.exceptions;

public class ErrorResponse {

    public ErrorResponse() {
        super();
    }
    public ErrorResponse(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse [status=" + status + ", message=" + message + "]";
    }
}
