package com.example.github_interface.exception;

import lombok.Getter;

public class ProviderServerErrorException extends GitHubInterfaceException {

    public ProviderServerErrorException(String message, int code) {
        super(message, code);
    }
}
