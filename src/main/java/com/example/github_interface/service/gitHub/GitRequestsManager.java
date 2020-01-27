package com.example.github_interface.service.gitHub;

import com.example.github_interface.exception.ProviderServerErrorException;
import com.example.github_interface.exception.RequestException;
import com.example.github_interface.model.GitRepository;
import com.example.github_interface.service.RestRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
public class GitRequestsManager {

    private RestRequests restRequests;
    private GitHubObjectsConverter gitHubObjectsConverter;

    @Autowired
    public GitRequestsManager(RestRequests restRequests, GitHubObjectsConverter gitHubObjectsConverter) {
        this.restRequests = restRequests;
        this.gitHubObjectsConverter = gitHubObjectsConverter;
    }

    public List<GitRepository> getUserAllRepositories(String username) throws RequestException, ProviderServerErrorException {

        List<Object> rowData = restRequests.getObjectsList("https://api.github.com/users/" + username + "/repos?type=all&page=1", HttpMethod.GET);
        List<GitRepository> gitRepositories = gitHubObjectsConverter.convertObjectsToGitRepositories(rowData);

        return gitRepositories;
    }


}
