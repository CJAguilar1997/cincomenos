package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class InvalidKeyException extends ResponseProductoException {

    public InvalidKeyException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
