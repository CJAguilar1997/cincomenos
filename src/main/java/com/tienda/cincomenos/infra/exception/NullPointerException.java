package com.tienda.cincomenos.infra.exception;

import org.springframework.http.HttpStatus;

public class NullPointerException extends ResponseLoggeableException{

    public NullPointerException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
