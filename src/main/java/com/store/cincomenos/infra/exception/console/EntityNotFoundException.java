package com.store.cincomenos.infra.exception.console;

public class EntityNotFoundException extends LoggeableException{

    public EntityNotFoundException(String reason) {
        super(reason);
    }

}
