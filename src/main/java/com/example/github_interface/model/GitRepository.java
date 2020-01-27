package com.example.github_interface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitRepository {

    private long id;
    private String name;
    private String full_name;
    private String html_url;
    private String description;
    private boolean fork;
    private String url;

}
