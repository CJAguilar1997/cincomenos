package com.store.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.dto.persona.login.DatosUsuarioLoginRespuesta;
import com.store.cincomenos.domain.persona.empleado.Empleado;


public record DatosRespuestaEmpleadoLogin(
    @JsonProperty(value = "datos_empleado", index = 0)
    DatosRespuestaEmpleado datos,
    @JsonProperty(value = "datos_login_empleado", index = 1)
    DatosUsuarioLoginRespuesta login
) {

    public DatosRespuestaEmpleadoLogin(Empleado empleado, DatosUsuarioLoginRespuesta datosLogin) {
        this(new DatosRespuestaEmpleado(empleado), datosLogin);
    }

}
