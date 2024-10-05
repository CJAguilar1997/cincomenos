package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.infra.exception.responsive.ResponseLoggeableException;

public class InvalidValueException extends ResponseLoggeableException {

    public InvalidValueException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
