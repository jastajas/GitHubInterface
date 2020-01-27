package com.example.github_interface.model.errorMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ErrorMessage {

    private String title;
    private String type;
    private ErrorMessageProperties properties;
    private String[] required;

}
