package com.tienda.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tienda.cincomenos.domain.dto.persona.login.DatosLoginRespuesta;
import com.tienda.cincomenos.domain.persona.empleado.Empleado;


public record DatosRespuestaEmpleadoLogin(
    @JsonProperty(value = "datos_empleado", index = 0)
    DatosRespuestaEmpleado datos,
    @JsonProperty(value = "datos_login_empleado", index = 1)
    DatosLoginRespuesta login
) {

    public DatosRespuestaEmpleadoLogin(Empleado empleado, DatosLoginRespuesta datosLogin) {
        this(new DatosRespuestaEmpleado(empleado), datosLogin);
    }

}
