package com.wellness.tracking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secret;
    private final int jwtExpirationInMs;
    private final Algorithm algorithm;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expirationInMs}") int jwtExpirationInMs) {
        this.secret = secret;
        this.jwtExpirationInMs = jwtExpirationInMs;
        algorithm = Algorithm.HMAC512(this.secret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        final long now = System.currentTimeMillis();
        User user = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + jwtExpirationInMs * 1000))
                .sign(algorithm);
    }

    public String validateTokenAndFetchUsername(String token) {
        return JWT.require(algorithm).build().verify(token).getSubject();
    }

}
