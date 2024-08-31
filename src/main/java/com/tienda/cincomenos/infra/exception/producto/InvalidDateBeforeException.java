package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.infra.exception.ResponseLoggeableException;

public class InvalidDateBeforeException extends ResponseLoggeableException {

    public InvalidDateBeforeException(HttpStatus status, String reason) {
        super(status, reason);
    }

}