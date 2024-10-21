package com.tienda.cincomenos.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda.cincomenos.domain.persona.login.Usuario;
import com.tienda.cincomenos.domain.persona.login.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null) {
            throw new RuntimeException("El nombre de usuario no puede ser nulo");
        }
        Usuario userEntity = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("El usuario %s no existe", email)));
        return userEntity;
    }

}
