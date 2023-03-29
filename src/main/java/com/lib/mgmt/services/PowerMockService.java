package com.lib.mgmt.services;

public class PowerMockService {

    private String privateMessage() {
        return "Hello World!";
    }

    public static String staticMessage() {
        return "Hello World!";
    }

    public final String finalMessage() {
        return "Hello World!";
    }
}
