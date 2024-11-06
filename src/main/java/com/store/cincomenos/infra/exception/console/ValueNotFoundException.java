package com.store.cincomenos.infra.exception.console;

public class ValueNotFoundException extends LoggeableException{

    public ValueNotFoundException(String reason) {
        super(reason);
    }

}
