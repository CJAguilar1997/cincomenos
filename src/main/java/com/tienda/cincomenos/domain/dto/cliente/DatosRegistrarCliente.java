package com.tienda.cincomenos.domain.dto.cliente;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.dto.persona.DatosDeContactoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosRegistrarCliente(
    @NotBlank
    @Pattern(regexp = "[\\p{L} ]+", message = "El nombre contiene caracteres invalidos")
    String nombre,

    @Pattern(regexp = "[0-9-]+", message = "El DNI contiene simbolos invalidos")
    String dni,

    @JsonAlias("datos_contacto")
    DatosDeContactoDTO contacto
) {

}
