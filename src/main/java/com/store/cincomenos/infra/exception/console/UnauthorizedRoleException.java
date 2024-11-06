package com.store.cincomenos.infra.exception.console;

public class UnauthorizedRoleException extends LoggeableException{

    public UnauthorizedRoleException(String reason) {
        super(reason);
    }
}
