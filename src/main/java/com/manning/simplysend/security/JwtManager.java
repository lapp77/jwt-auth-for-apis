package com.manning.simplysend.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtManager {

    private static final String ISSUER = "simplysend";

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtManager(@Value("${jwt.signing.key}") String signingKey) {
        this.algorithm = Algorithm.HMAC256(signingKey);
        this.verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    }

    String create(Map<String, Object> payloadClaims) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withPayload(payloadClaims)
                .sign(algorithm);
    }

    DecodedJWT verify(String token) {
        return verifier.verify(token);
    }
}
