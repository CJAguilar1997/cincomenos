package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class InvalidValueException extends ResponseProductoException {

    public InvalidValueException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
