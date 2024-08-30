package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class InvalidKeyException extends ResponseProductoLoggeableException {

    public InvalidKeyException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
