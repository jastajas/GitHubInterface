package com.example.github_interface.model.errorMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ErrorMessageProperties {

    private SingleMessageProperty errorCode;
    private SingleMessageProperty errorMessage;

}
