package com.store.cincomenos.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.domain.persona.login.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String token = authHeader.replace("Bearer ", "");
            String idUsername = tokenService.getSubject(token);
            if (idUsername != null) {
                String[] split = idUsername.split("_");
                User user = usuarioRepository.findByIdAndUsername(split[0], split[1]).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(idUsername, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
