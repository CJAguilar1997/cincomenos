package com.store.cincomenos.infra.exception.console.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import com.store.cincomenos.infra.exception.LoggeableExceptionImpl;

public class ResponseLoggeableException extends ResponseStatusException implements LoggeableExceptionImpl{

    public ResponseLoggeableException(HttpStatusCode status, String reason) {
        super(status, reason);
        StackTraceElement element = getStackTrace()[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(LocalDateTime.now());
        logError("Error: %s; inClass: %s; inMethod: %s; atLine: %d; onDate: %s: %s", 
            this.getClass().getName(), 
            element.getClassName(), 
            element.getMethodName(),
            element.getLineNumber(),
            formattedDateTime,
            reason);
    }

}
