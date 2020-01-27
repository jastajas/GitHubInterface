package com.example.github_interface.model.application_endpoint;

import lombok.*;

import java.util.List;

//@Getter
//@Builder(toBuilder = true)
@Data
public class ApplicationEndpoint {

    private String methodName;//h
    private List<MethodParameter> parameterList;//h
    private String returnedType;//h
    private List<String> pathName;//M
    private String method;//M

}
