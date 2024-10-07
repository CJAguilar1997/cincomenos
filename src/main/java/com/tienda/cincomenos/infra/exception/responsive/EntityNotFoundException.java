package com.tienda.cincomenos.infra.exception.responsive;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ResponseLoggeableException{

    public EntityNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

}