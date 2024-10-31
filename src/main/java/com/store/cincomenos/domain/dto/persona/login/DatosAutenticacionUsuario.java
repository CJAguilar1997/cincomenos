package com.store.cincomenos.domain.dto.persona.login;

import jakarta.validation.constraints.Email;

public record DatosAutenticacionUsuario(
    @Email
    String email,
    String password
) {

}