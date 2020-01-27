package com.example.github_interface.model.application_endpoint;


import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class MethodParameter {

    private String name;
    private String typeName;
    private String[] annotations;

}
