package com.tienda.cincomenos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.cincomenos.domain.dto.persona.login.DatosAutenticacionUsuario;
import com.tienda.cincomenos.domain.persona.login.Usuario;
import com.tienda.cincomenos.infra.security.DatosJwtToken;
import com.tienda.cincomenos.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping
    public ResponseEntity<Object> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
            Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.password());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var jwtToken = tokenService.generateToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJwtToken(jwtToken));
    }
}
