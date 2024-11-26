package com.store.cincomenos.infra.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private ErrorCode errorCode;

    public ErrorResponse(String message, ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String toString() {
        return String.format("MESSAGE: %s, ERROR: %s", this.message, this.errorCode.name().toString());
    }
}
