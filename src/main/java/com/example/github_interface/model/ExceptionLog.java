package com.example.github_interface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ExceptionLog {

    private Timestamp timestamp;
    private String exception;
    private String packageName;
    private String message;

}
