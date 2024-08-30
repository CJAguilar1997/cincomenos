package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class InvalidDateBeforeException extends ResponseProductoException {

    public InvalidDateBeforeException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
