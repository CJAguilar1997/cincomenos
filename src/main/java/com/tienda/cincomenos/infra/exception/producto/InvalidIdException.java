package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class InvalidIdException extends ResponseProductoException{

    public InvalidIdException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
