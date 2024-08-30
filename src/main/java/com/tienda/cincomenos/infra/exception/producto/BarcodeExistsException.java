package com.tienda.cincomenos.infra.exception.producto;

import org.springframework.http.HttpStatus;

public class BarcodeExistsException extends ResponseProductoException {

    public BarcodeExistsException(HttpStatus status, String reason) {
        super(status, reason);
    }

}
