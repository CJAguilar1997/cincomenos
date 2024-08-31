package com.tienda.cincomenos.infra.exception;

import org.springframework.http.HttpStatus;

public class ValueNotFoundException extends ResponseLoggeableException{

    public ValueNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
