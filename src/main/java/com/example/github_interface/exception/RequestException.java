package com.example.github_interface.exception;

public class RequestException extends GitHubInterfaceException {

    public RequestException(RequestExceptionCode requestExceptionCode) {
        super(requestExceptionCode.getMessage(), requestExceptionCode.getCode());
    }
}
