package com.example.github_interface.service.endpoints;

import com.example.github_interface.repository.endpoint.ApplicationEndpointsRepository;
import com.example.github_interface.model.application_endpoint.ApplicationEndpoint;
import com.example.github_interface.model.application_endpoint.MethodParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Parameter;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationEndpointsFactory {

    private ApplicationContext applicationContext;
    private ApplicationEndpointsRepository applicationEndpointsRepository;

    @Autowired
    public ApplicationEndpointsFactory(ApplicationContext applicationContext, ApplicationEndpointsRepository applicationEndpointsRepository) {
        this.applicationContext = applicationContext;
        this.applicationEndpointsRepository = applicationEndpointsRepository;
    }

    /**
     * Main method for preparing all mapped application endpoints.
     */
    public void createApplicationEndpointsList() {

        applicationContext
                .getBean(RequestMappingHandlerMapping.class)
                .getHandlerMethods()
                .forEach(this::createEndpointsList);
    }

    /**
     * Method preparing endpoints component information and saving endpoints information in repository.
     * @param requestMappingInfo RequestMappingInfo
     * @param handlerMethod HandlerMethod
     */

    private void createEndpointsList(RequestMappingInfo requestMappingInfo, HandlerMethod handlerMethod) {

        ApplicationEndpoint applicationEndpoint = new ApplicationEndpoint();

        Optional.ofNullable(handlerMethod)
                .ifPresent(handler_method -> prepareMethodInfo(handler_method, applicationEndpoint));

        Optional.ofNullable(requestMappingInfo)
                .ifPresent(request_mapping_info -> prepareMappingInfo(request_mapping_info, applicationEndpoint));

        applicationEndpointsRepository.addApplicationEndpoint(applicationEndpoint);

    }


    private void prepareMethodInfo(HandlerMethod handlerMethod, ApplicationEndpoint applicationEndpoint) {

        List<MethodParameter> methodParameters = Arrays.stream(handlerMethod.getMethod().getParameters())
                .map(parameter -> prepareMethodParameter(parameter))
                .collect(Collectors.toList());

        String returnClassName = Optional.ofNullable(handlerMethod.getMethod().getGenericReturnType())
                .map(type -> type.getTypeName())
                .orElse("");

        applicationEndpoint.setMethodName(handlerMethod.getMethod().getName());
        applicationEndpoint.setReturnedType(returnClassName);
        applicationEndpoint.setParameterList(methodParameters);

    }

    /**
     *
     * @param parameter
     * @return
     */
    private MethodParameter prepareMethodParameter(Parameter parameter) {


        String[] annotationNames = Arrays.stream(parameter.getAnnotations())
                .map(annotation -> annotation.annotationType().getSimpleName())
                .toArray(String[]::new);

        return MethodParameter.builder()
                .name(parameter.getName())
                .typeName(parameter.getType().getSimpleName())
                .annotations(annotationNames)
                .build();
    }

    /**
     *
     * @param requestMappingInfo
     * @param applicationEndpoint
     */
    private void prepareMappingInfo(RequestMappingInfo requestMappingInfo, ApplicationEndpoint applicationEndpoint) {


        String requestMethod = requestMappingInfo
                .getMethodsCondition()
                .getMethods()
                .stream()
                .map(request_method -> request_method.name())
                .findFirst()
                .orElse("");

        List<String> pattern = requestMappingInfo
                .getPatternsCondition()
                .getPatterns()
                .stream()
                .collect(Collectors.toList());

        applicationEndpoint.setMethod(requestMethod);
        applicationEndpoint.setPathName(pattern);

    }


}
