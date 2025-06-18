package com.store.cincomenos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.cincomenos.domain.dto.persona.login.DataAuthenticationUser;
import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.infra.security.DataJwtToken;
import com.store.cincomenos.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid DataAuthenticationUser dataAuthenticationUser) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(dataAuthenticationUser.email(), dataAuthenticationUser.password());
        var authUser = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new DataJwtToken(jwtToken));  
    }
}
