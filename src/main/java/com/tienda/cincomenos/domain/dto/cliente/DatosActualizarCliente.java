package com.tienda.cincomenos.domain.dto.cliente;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.dto.persona.DatosDeContactoDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosActualizarCliente(
    @NotNull
    @Min(1)
    @Max(10)
    Long id,

    @Pattern(regexp = "[\\p{L} ]+", message = "El nombre contiene caracteres invalidos")
    String nombre,

    @Pattern(regexp = "[0-9-]+", message = "El DNI contiene simbolos invalidos")
    String dni,
    
    @JsonAlias("datos_contacto")
    DatosDeContactoDTO contacto

) implements DatosActualizar {

    @Override
    public Map<String, Object> getAtributos() {
        return DatosActualizar.super.getAtributos();
    }

}
