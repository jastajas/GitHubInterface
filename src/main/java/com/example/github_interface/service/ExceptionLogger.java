package com.example.github_interface.service;

import com.example.github_interface.model.ExceptionLog;
import com.example.github_interface.repository.exception.ExceptionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ExceptionLogger {

    private ExceptionLogRepository exceptionLogRepository;

    @Autowired
    public ExceptionLogger(ExceptionLogRepository exceptionLogRepository) {
        this.exceptionLogRepository = exceptionLogRepository;
    }

    public void logException(Exception exception) {
        exactExceptionLog(exception, exception.getMessage());
    }

    public void logException(Exception exception, String additionalMessage) {

        String message = exception.getMessage() + " /" + additionalMessage;

        exactExceptionLog(exception, message);
    }

    private void exactExceptionLog(Exception exception, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        ExceptionLog exceptionLog = ExceptionLog.builder()
                .timestamp(timestamp)
                .exception(exception.getClass().getSimpleName())
                .packageName(exception.getClass().getPackage().getName())
                .message(message)
                .build();

        exceptionLogRepository.addExceptionLog(exceptionLog);

    }


}
