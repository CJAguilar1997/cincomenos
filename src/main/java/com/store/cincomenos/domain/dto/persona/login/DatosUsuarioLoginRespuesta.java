package com.store.cincomenos.domain.dto.persona.login;

import com.store.cincomenos.domain.persona.login.Usuario;

public record DatosUsuarioLoginRespuesta(
    Long id,
    String email,
    String username,
    String password
) {

    public DatosUsuarioLoginRespuesta(Usuario userLogin, String password) {
        this(userLogin.getIdUsuario(), userLogin.getEmail(), userLogin.getPlainUsername(), password);
    }

}
