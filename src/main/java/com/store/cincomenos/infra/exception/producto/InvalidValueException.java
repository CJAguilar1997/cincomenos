package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class InvalidValueException extends LoggeableException {

    public InvalidValueException(String reason) {
        super(reason);
    }

}
