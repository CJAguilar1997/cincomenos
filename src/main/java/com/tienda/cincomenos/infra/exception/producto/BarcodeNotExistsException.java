package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class BarcodeNotExistsException extends ResponseProductoLoggeableException {

    public BarcodeNotExistsException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
