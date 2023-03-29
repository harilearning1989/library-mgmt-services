package com.lib.mgmt.exceptions;

public class StudentNotFoundException extends RuntimeException {

    private String message;

    public StudentNotFoundException() {}

    public StudentNotFoundException(String msg){
        super(msg);
        this.message = msg;
    }
}