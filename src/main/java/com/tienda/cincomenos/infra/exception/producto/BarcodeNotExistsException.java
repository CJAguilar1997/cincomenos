package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.infra.exception.ResponseLoggeableException;

public class BarcodeNotExistsException extends ResponseLoggeableException {

    public BarcodeNotExistsException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
