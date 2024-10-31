package com.store.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

import com.store.cincomenos.infra.exception.responsive.ResponseLoggeableException;

public class OutOfStockException extends ResponseLoggeableException{

    public OutOfStockException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
