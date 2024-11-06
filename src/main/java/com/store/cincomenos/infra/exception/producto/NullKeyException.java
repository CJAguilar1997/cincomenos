package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class NullKeyException extends LoggeableException{

    public NullKeyException(String reason) {
        super(reason);
    }

}
