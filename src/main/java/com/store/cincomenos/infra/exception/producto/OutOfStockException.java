package com.store.cincomenos.infra.exception.producto;

import com.store.cincomenos.infra.exception.console.LoggeableException;

public class OutOfStockException extends LoggeableException{

    public OutOfStockException(String reason) {
        super(reason);
    }

}
