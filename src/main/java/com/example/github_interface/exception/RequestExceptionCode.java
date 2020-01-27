package com.example.github_interface.exception;

public enum RequestExceptionCode {

    NO_PARAMETER_PROVIDED("No parameter provided. Pleases provide required parameters.", 1),
    INCORRECT_PARAMETER("Incorrect parameter provided. Pleases correct parameter.", 2);

    private String message;
    private int code;

    RequestExceptionCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
