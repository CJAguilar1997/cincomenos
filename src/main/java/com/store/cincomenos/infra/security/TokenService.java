package com.store.cincomenos.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.store.cincomenos.domain.persona.login.User;

@Service
public class TokenService {

    @Value("${cincomenos.security.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("cincomenos")
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(generateTimeExpiration())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error al generar el token");
        }
    }

    private DecodedJWT validateToken(String jwtToken) {
        if (jwtToken == null) {
            throw new JWTDecodeException("No hay un token valido");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("cincomenos")
                .build()
                .verify(jwtToken);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("El token brindado es invalido");
        }
    }

    public String getSubject(String jwtToken) {
        DecodedJWT verifier = validateToken(jwtToken);
        return verifier.getSubject();
    }

    private Instant generateTimeExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }
}
