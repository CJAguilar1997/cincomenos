package com.store.cincomenos.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.domain.persona.login.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null) {
            throw new RuntimeException("El nombre de usuario no puede ser nulo");
        }
        User userEntity = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("El usuario %s no existe", email)));
        return userEntity;
    }

}
