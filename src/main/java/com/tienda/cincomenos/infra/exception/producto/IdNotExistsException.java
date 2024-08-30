package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class IdNotExistsException extends ResponseProductoLoggeableException{

    public IdNotExistsException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
