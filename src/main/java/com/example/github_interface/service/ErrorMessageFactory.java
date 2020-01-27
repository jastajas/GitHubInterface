package com.example.github_interface.service;

import com.example.github_interface.exception.GitHubInterfaceException;
import com.example.github_interface.model.errorMessage.ErrorMessage;
import com.example.github_interface.model.errorMessage.ErrorMessageProperties;
import com.example.github_interface.model.errorMessage.SingleMessageProperty;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ErrorMessageFactory {

    public ErrorMessage createErrorMessage(GitHubInterfaceException e, HttpStatus status) {

        String[] required = {"errorCode", "errorMessage"};

        return ErrorMessage.builder()
                .title(e.getClass().getSimpleName())
                .type(status.name())
                .properties(this.createErrorMessageProperties(e))
                .required(required)
                .build();
    }


    private ErrorMessageProperties createErrorMessageProperties(GitHubInterfaceException e) {

        String message = null != e.getMessage() ? e.getMessage() : "No message provided";

        return ErrorMessageProperties.builder()
                .errorCode(new SingleMessageProperty(String.valueOf(e.getCode())))
                .errorMessage(new SingleMessageProperty(message))
                .build();
    }


}
