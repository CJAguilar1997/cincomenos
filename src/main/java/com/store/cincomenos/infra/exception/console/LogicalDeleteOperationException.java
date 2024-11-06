package com.store.cincomenos.infra.exception.console;

public class LogicalDeleteOperationException extends LoggeableException {

    public LogicalDeleteOperationException(String reason) {
        super(reason);
    }
}
