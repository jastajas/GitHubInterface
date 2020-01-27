package com.example.github_interface.repository.endpoint;

import com.example.github_interface.model.application_endpoint.ApplicationEndpoint;

import lombok.Data;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class ApplicationEndpointsRepository {

    private List<ApplicationEndpoint> applicationEndpoints;

    public ApplicationEndpointsRepository() {
        this.applicationEndpoints = new ArrayList<>();
    }

    public void addApplicationEndpoint(ApplicationEndpoint applicationEndpoint) {

        applicationEndpoints.add(applicationEndpoint);

        //TODO Zmiana na logikę prntera
        System.out.println("SUCCESS: Application endpoint added to repository: " + applicationEndpoint.toString());
    }

}
