package com.lib.mgmt.mock;

public class ClassWithPrivateMethods {

    private String printMessage(String message) {
        return message;
    }

    public String privateCall(String message) {
        return printMessage(message);
    }
}
