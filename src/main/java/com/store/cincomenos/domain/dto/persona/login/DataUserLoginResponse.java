package com.store.cincomenos.domain.dto.persona.login;

import com.store.cincomenos.domain.persona.login.User;

public record DataUserLoginResponse(
    Long id,
    String email,
    String username,
    String password
) {

    public DataUserLoginResponse(User userLogin, String password) {
        this(userLogin.getId(), userLogin.getEmail(), userLogin.getPlainUsername(), password);
    }

}
