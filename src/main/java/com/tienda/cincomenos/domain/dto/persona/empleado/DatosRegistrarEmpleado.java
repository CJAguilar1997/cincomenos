package com.tienda.cincomenos.domain.dto.persona.empleado;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.dto.persona.DatosDeContactoDTO;
import com.tienda.cincomenos.domain.persona.DatosRegistrar;
import com.tienda.cincomenos.domain.persona.empleado.PuestoTrabajo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistrarEmpleado(
    
    @NotBlank
    @Pattern(regexp = "^\\w+\\s+\\w+.*$", message = "El nombre debe de contener al menos un nombre y un apellido")
    String nombre,

    @NotBlank
    @Pattern(regexp = "[0-9]+", message = "El DNI contiene simbolos invalidos")
    String dni,

    @NotNull
    @JsonAlias("puesto_trabajo")
    PuestoTrabajo puestoTrabajo,

    @NotNull
    Set<String> roles,
    
    @JsonAlias("datos_contacto")
    DatosDeContactoDTO contacto

) implements DatosRegistrar {
    
}
