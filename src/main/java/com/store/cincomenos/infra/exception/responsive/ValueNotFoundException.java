package com.store.cincomenos.infra.exception.responsive;

import org.springframework.http.HttpStatus;

public class ValueNotFoundException extends ResponseLoggeableException{

    public ValueNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
