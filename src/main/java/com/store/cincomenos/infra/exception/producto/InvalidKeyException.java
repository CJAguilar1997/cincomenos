package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class InvalidKeyException extends LoggeableException {

    public InvalidKeyException(String reason) {
        super(reason);
    }

}
