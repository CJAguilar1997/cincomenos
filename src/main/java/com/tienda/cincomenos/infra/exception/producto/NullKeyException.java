package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class NullKeyException extends ResponseProductoException{

    public NullKeyException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
