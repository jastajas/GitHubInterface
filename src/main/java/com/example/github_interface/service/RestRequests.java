package com.example.github_interface.service;

import com.example.github_interface.exception.ProviderServerErrorException;
import com.example.github_interface.exception.RequestException;
import com.example.github_interface.exception.RequestExceptionCode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * RestRequest class represents service for general rest request for all object types or list of objects.
 */
@Service
public class RestRequests {

    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    public RestRequests() {
        this.restTemplate = new RestTemplate();

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });

        mapper = new ObjectMapper();
    }

    /**
     * @param url String representing url
     * @param method http method
     * @return general objects list
     * @throws RequestException in case of delivering incorrect username (4** http status code)
     * @throws ProviderServerErrorException in case of exception of providers server error (5xx http status).
     */
    public List<Object> getObjectsList(String url, HttpMethod method) throws RequestException, ProviderServerErrorException {


        ResponseEntity<Object> response = null;

            response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    Object.class);

            if(response.getStatusCode().is4xxClientError()) {
                throw new RequestException(RequestExceptionCode.INCORRECT_PARAMETER);
            }else if (response.getStatusCode().is5xxServerError()){
                throw new ProviderServerErrorException("Git server error.", 4);
            }

            return mapper.convertValue(response.getBody(), new TypeReference<List<Object>>() {
            });

    }

    /**
     * @param url String representing url
     * @param method http method
     * @return general object
     * @throws RequestException in case of delivering incorrect username (4** http status code)
     * @throws ProviderServerErrorException in case of exception of providers server error (5xx http status).
     */
    public Object getSimpleObject(String url, HttpMethod method) throws RequestException, ProviderServerErrorException {

        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                method,
                null,
                Object.class);

        if(response.getStatusCode().is4xxClientError()) {
            throw new RequestException(RequestExceptionCode.INCORRECT_PARAMETER);
        }else if (response.getStatusCode().is5xxServerError()){
            throw new ProviderServerErrorException("Git server error.", 4);
        }

        return response.getBody();
    }

}
