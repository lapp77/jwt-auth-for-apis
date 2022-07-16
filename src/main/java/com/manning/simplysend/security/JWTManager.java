package com.manning.simplysend.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JWTManager {

    private final Algorithm algorithm;

    public JWTManager(@Value("${jwt.signing.key}") String signingKey) {
        this.algorithm = Algorithm.HMAC256(signingKey);
    }

    String create(Map<String, Object> payloadClaims) {
        return JWT.create()
                .withIssuer("simplysend")
                .withPayload(payloadClaims)
                .sign(algorithm);
    }
}
