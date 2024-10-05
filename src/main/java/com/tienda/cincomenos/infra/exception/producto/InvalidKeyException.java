package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.infra.exception.responsive.ResponseLoggeableException;

public class InvalidKeyException extends ResponseLoggeableException {

    public InvalidKeyException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
