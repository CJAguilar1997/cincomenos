package com.tienda.cincomenos.infra.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ResponseLoggeableException{

    public EntityNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
