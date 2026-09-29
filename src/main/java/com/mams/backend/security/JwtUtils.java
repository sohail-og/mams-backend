package com.mams.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(authToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
