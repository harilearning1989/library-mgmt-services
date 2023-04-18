package com.lib.mgmt.exceptions;

import org.springframework.http.HttpStatus;

public class GlobalMessageException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public GlobalMessageException() {}

    public GlobalMessageException(String msg, HttpStatus status) {
        super(msg);
        this.message = msg;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
