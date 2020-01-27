package com.example.github_interface.exception;

import lombok.Getter;

@Getter
public abstract class GitHubInterfaceException extends Exception {

    private int code;

    public GitHubInterfaceException(String message, int code) {
        super(message);
        this.code = code;
    }
}
