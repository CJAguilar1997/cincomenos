package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class BarcodeNotExistsException extends LoggeableException {

    public BarcodeNotExistsException(String reason) {
        super(reason);
    }

}
