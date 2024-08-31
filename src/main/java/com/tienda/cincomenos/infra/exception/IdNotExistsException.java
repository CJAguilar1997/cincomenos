package com.tienda.cincomenos.infra.exception;

import org.springframework.http.HttpStatus;

public class IdNotExistsException extends ResponseLoggeableException{

    public IdNotExistsException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
