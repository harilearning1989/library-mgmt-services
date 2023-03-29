package com.lib.mgmt.exceptions;

public class BookAlreadyIssuedException extends RuntimeException {

    private String message;

    public BookAlreadyIssuedException() {}

    public BookAlreadyIssuedException(String msg){
        super(msg);
        this.message = msg;
    }
}