package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class NullKeyException extends ResponseProductoLoggeableException{

    public NullKeyException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
