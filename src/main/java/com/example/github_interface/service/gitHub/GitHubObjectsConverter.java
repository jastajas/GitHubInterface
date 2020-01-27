package com.example.github_interface.service.gitHub;

import com.example.github_interface.model.GitRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitHubObjectsConverter {

    private ObjectMapper mapper;

    /**
     * @param mapper ObjectMapper class for mapping general object to particular object type.
     */
    @Autowired
    public GitHubObjectsConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Method convert List of Objects to List of GitRepositories.
     * @param objects represent List of general objects
     * @return List of GitRepositories Objects
     */

    public List<GitRepository> convertObjectsToGitRepositories(List<Object> objects) {

        if (null != objects && objects.size() > 0) {
            return mapper.convertValue(objects, new TypeReference<List<GitRepository>>() {
            });
        }

        return null;
    }

    /**
     * Method convert Object to GitRepository.
     * @param object represent general objects
     * @return GitRepository object
     */
    public GitRepository convertObjectToGitRepository(Object object) {

        if (null != object) {
            return mapper.convertValue(object, new TypeReference<GitRepository>() {
            });
        }

        return null;
    }



}
