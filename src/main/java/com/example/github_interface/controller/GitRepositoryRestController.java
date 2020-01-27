package com.example.github_interface.controller;

import com.example.github_interface.exception.ProviderServerErrorException;
import com.example.github_interface.exception.RequestException;
import com.example.github_interface.exception.RequestExceptionCode;
import com.example.github_interface.model.GitRepository;

import com.example.github_interface.model.errorMessage.ErrorMessage;
import com.example.github_interface.service.ErrorMessageFactory;
import com.example.github_interface.service.ExceptionLogger;
import com.example.github_interface.service.gitHub.GitRequestsManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class GitRepositoryRestController {

    private GitRequestsManager gitRequestsManager;
    private ExceptionLogger exceptionLogger;
    private ErrorMessageFactory errorMessageFactory;

    @Autowired
    public GitRepositoryRestController(GitRequestsManager gitRequestsManager, ExceptionLogger exceptionLogger, ErrorMessageFactory errorMessageFactory) {
        this.gitRequestsManager = gitRequestsManager;
        this.exceptionLogger = exceptionLogger;
        this.errorMessageFactory = errorMessageFactory;
    }

    /**
     * Method provides objects representing simplified information about github repositories for particular user.
     *
     * @param username String representing username of regitered in github user.
     * @return GitRepository list
     * @throws RequestException in case of delivering incorrect username or in case of lack of this parameter
     */
    @GetMapping(value = {"/api/repositories/", "/api/repositories", "/api/repositories/{username}"})
    public ResponseEntity<List<GitRepository>> getAllRepositoriesByUser(@PathVariable(required = false) String username) throws RequestException, ProviderServerErrorException {

        if (null == username || "".equals(username))
            throw new RequestException(RequestExceptionCode.NO_PARAMETER_PROVIDED);

        List<GitRepository> gitRepositories = gitRequestsManager.getUserAllRepositories(username)
                                                                .stream()
                                                                .filter(gitRepository -> !gitRepository.isFork())
                                                                .collect(Collectors.toList());

        return ResponseEntity.ok(gitRepositories);
    }


    /**
     * Method handles exception of providers server error.
     *
     * @param e represent thrown ProviderServerErrorException object.
     * @return ErrorMessage object providing basic information about exception.
     */
    @ExceptionHandler(ProviderServerErrorException.class)
    private ResponseEntity<ErrorMessage> handleProviderServerErrorException(ProviderServerErrorException e) {

        exceptionLogger.logException(e);

        ErrorMessage message = errorMessageFactory.createErrorMessage(e, HttpStatus.SERVICE_UNAVAILABLE);

        return new ResponseEntity(message, HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Method handles exception of requests. Exception is thrown in case of delivering incorrect username or in case of lack of this parameter
     *
     * @param e represent thrown RequestException object.
     * @return ErrorMessage object providing basic information about exception.
     */
    @ExceptionHandler(RequestException.class)
    private ResponseEntity<ErrorMessage> handleRequestExceptions(RequestException e) {

        exceptionLogger.logException(e);

        ErrorMessage message = errorMessageFactory.createErrorMessage(e, HttpStatus.BAD_REQUEST);

        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }





}
