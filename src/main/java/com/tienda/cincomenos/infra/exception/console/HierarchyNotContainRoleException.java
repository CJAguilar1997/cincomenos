package com.tienda.cincomenos.infra.exception.console;

import org.springframework.http.HttpStatus;

public class HierarchyNotContainRoleException extends LoggeableException{

    public HierarchyNotContainRoleException(HttpStatus status, String reason) {
        super(reason);
    }

}
