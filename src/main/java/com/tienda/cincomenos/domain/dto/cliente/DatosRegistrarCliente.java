package com.tienda.cincomenos.domain.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosRegistrarCliente(
    @NotBlank
    @Pattern(regexp = "[\\p{L} ]+", message = "El nombre contiene caracteres invalidos")
    String nombre,

    @Pattern(regexp = "[0-9-]+", message = "El DNI contiene simbolos invalidos")
    String dni,

    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "\\+?[0-9- ]+", message = "El números contiene simbolos invalidos")
    String telefono
) {

}
