package com.store.cincomenos.domain.dto.persona.login;

import com.store.cincomenos.domain.persona.login.User;

public record DatosUsuarioLoginRespuesta(
    Long id,
    String email,
    String username,
    String password
) {

    public DatosUsuarioLoginRespuesta(User userLogin, String password) {
        this(userLogin.getId(), userLogin.getEmail(), userLogin.getPlainUsername(), password);
    }

}
