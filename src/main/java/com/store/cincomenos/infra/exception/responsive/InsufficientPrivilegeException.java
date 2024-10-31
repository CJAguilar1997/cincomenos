package com.store.cincomenos.infra.exception.responsive;

import org.springframework.http.HttpStatus;

public class InsufficientPrivilegeException extends ResponseLoggeableException{

    public InsufficientPrivilegeException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
