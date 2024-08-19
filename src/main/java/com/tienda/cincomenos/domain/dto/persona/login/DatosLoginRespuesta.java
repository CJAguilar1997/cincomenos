package com.tienda.cincomenos.domain.dto.persona.login;

import com.tienda.cincomenos.domain.persona.empleado.login.DatosLogin;

public record DatosLoginRespuesta(
    Long id,
    String username,
    String password
) {

    public DatosLoginRespuesta(DatosLogin userLogin) {
        this(userLogin.getIdCuenta(), userLogin.getUsername(), userLogin.getPassword());
    }

}
