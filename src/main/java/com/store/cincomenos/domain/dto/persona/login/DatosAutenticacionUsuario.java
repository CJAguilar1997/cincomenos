package com.store.cincomenos.domain.dto.persona.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DatosAutenticacionUsuario(
    @Email
    String email,
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}", message = "The password contains invalid characters")
    String password
) {

}