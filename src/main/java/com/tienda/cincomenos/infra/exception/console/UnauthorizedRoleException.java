package com.tienda.cincomenos.infra.exception.console;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.infra.exception.responsive.ResponseLoggeableException;

public class UnauthorizedRoleException extends ResponseLoggeableException{

    public UnauthorizedRoleException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
