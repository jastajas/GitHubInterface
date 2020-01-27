package com.example.github_interface;

import com.example.github_interface.service.endpoints.ApplicationEndpointsFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GithubInterfaceApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(GithubInterfaceApplication.class, args);

        ApplicationEndpointsFactory endpointsFactory = ctx.getBean(ApplicationEndpointsFactory.class);
        endpointsFactory.createApplicationEndpointsList();
    }

}
