package com.tienda.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.dto.persona.ActualizarDatosDeContacto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarEmpleado(
    @NotNull
    Long id,
    @JsonAlias("datos_contacto")
    ActualizarDatosDeContacto contacto
) {

}
