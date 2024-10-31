package com.store.cincomenos.domain.dto.persona;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ActualizarDatosDeContacto(
    
    @Pattern(regexp = "\\+?[0-9 ]+", message = "El números contiene simbolos invalidos")
    String telefono,
    @Email
    String email
) {

}
