package com.store.cincomenos.infra.exception.console;

public class InsufficientPrivilegeException extends LoggeableException{

    public InsufficientPrivilegeException(String reason) {
        super(reason);
    }

}
