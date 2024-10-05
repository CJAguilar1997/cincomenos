package com.tienda.cincomenos.domain.persona.login;

public enum ERoles {
    OWNER,
    ADMIN,
    EMPLOYEE,
    USER;

    public static ERoles toERoles(String rol) {
        return ERoles.valueOf(rol.toUpperCase());
    }
}
