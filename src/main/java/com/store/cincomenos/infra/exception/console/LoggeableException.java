package com.store.cincomenos.infra.exception.console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.store.cincomenos.infra.exception.LoggeableExceptionImpl;

public class LoggeableException extends RuntimeException implements LoggeableExceptionImpl{
    
    public LoggeableException(String reason) {
        super(reason);
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
