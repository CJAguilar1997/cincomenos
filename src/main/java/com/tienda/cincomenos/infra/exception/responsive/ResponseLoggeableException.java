package com.tienda.cincomenos.infra.exception.responsive;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.infra.exception.LoggeableExceptionImpl;

public class ResponseLoggeableException extends ResponseStatusException implements LoggeableExceptionImpl{

    public ResponseLoggeableException(HttpStatus status, String reason) {
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
