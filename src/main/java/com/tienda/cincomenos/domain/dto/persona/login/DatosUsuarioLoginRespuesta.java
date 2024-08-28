package com.tienda.cincomenos.domain.dto.persona.login;

import com.tienda.cincomenos.domain.persona.login.Usuario;

public record DatosUsuarioLoginRespuesta(
    Long id,
    String email,
    String username,
    String password
) {

    public DatosUsuarioLoginRespuesta(Usuario userLogin, String password) {
        this(userLogin.getIdUsuario(), userLogin.getEmail(), userLogin.getUsername(), password);
    }

}
