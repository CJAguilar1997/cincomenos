package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class InvalidDateBeforeException extends LoggeableException {

    public InvalidDateBeforeException(String reason) {
        super(reason);
    }

}
