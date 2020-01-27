package com.example.github_interface.controller;

import com.example.github_interface.exception.EndpointsInfoException;
import com.example.github_interface.model.ExceptionLog;
import com.example.github_interface.model.application_endpoint.ApplicationEndpoint;
import com.example.github_interface.model.errorMessage.ErrorMessage;
import com.example.github_interface.repository.endpoint.ApplicationEndpointsRepository;
import com.example.github_interface.repository.exception.ExceptionLogRepository;
import com.example.github_interface.service.ErrorMessageFactory;
import com.example.github_interface.service.ExceptionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainAppController {

    private ApplicationEndpointsRepository applicationEndpointsRepository;
    private ExceptionLogRepository exceptionLogRepository;
    private ExceptionLogger exceptionLogger;
    private ErrorMessageFactory errorMessageFactory;

    @Autowired
    public MainAppController(ApplicationEndpointsRepository applicationEndpointsRepository, ExceptionLogRepository exceptionLogRepository,
                             ExceptionLogger exceptionLogger, ErrorMessageFactory errorMessageFactory) {
        this.applicationEndpointsRepository = applicationEndpointsRepository;
        this.exceptionLogRepository = exceptionLogRepository;
        this.exceptionLogger = exceptionLogger;
        this.errorMessageFactory = errorMessageFactory;
    }

    /**
     * Method provides list of objects representing application endpoints information.
     *
     * @return ApplicationEndpoint list.
     * @throws EndpointsInfoException if endpoints information aren't available.
     */
    @GetMapping(value = {"/", "", "/api/", "/api", "/api/allEndpoints"})
    public ResponseEntity<List<ApplicationEndpoint>> showAllAppEndpoints() throws EndpointsInfoException {

        if (applicationEndpointsRepository.getApplicationEndpoints().isEmpty()) {

            throw new EndpointsInfoException("No endpoints information is generated. Please contact with app admin.", 3);
        }

        return ResponseEntity.ok(applicationEndpointsRepository.getApplicationEndpoints());
    }


    /**
     * Method handles exception of endpoints information. Exception is thrown if these information aren't available.
     *
     * @param e represent thrown EndpointsInfoException object.
     * @return ErrorMessage object providing basic information about exception.
     */
    @ExceptionHandler(EndpointsInfoException.class)
    private ResponseEntity<ErrorMessage> handleEndpointInfoExceptions(EndpointsInfoException e) {

        exceptionLogger.logException(e);

        ErrorMessage message = errorMessageFactory.createErrorMessage(e, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method provides all log of thrown exceptions.
     * @return ExceptionLogs list
     */
    @GetMapping(value = {"/printExceptionLogs"})
    public ResponseEntity<List<ExceptionLog>> showAllExceptionLogs() {

        return ResponseEntity.ok(exceptionLogRepository.getExceptionLogs());
    }

}
