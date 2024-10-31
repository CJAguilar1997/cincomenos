package com.store.cincomenos.domain.dto.persona.cliente;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.DatosDeContactoDTO;
import com.store.cincomenos.domain.persona.DatosRegistrar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosRegistrarCliente(
    @NotBlank
    @Pattern(regexp = "^\\w+\\s+\\w+.*$", message = "El nombre contiene caracteres invalidos")
    String nombre,

    @NotBlank
    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "El DNI contiene simbolos invalidos")
    String dni,

    @JsonAlias("datos_contacto")
    DatosDeContactoDTO contacto
) implements DatosRegistrar{

}
