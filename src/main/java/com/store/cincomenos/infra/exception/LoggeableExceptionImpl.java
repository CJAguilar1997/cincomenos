package com.store.cincomenos.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoggeableExceptionImpl {
    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    default void logError(String reason, Object... args) {
        getLogger().error(String.format(reason, args));
    }
}
