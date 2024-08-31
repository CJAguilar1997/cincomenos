package com.tienda.cincomenos.infra.exception;

import org.springframework.http.HttpStatus;

public class NullDataException extends ResponseLoggeableException{

    public NullDataException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
