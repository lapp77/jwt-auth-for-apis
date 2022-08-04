package com.manning.simplysend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtManager(@Value("\${jwt.signing.key}") signingKey: String) {

    private val algorithm = Algorithm.HMAC256(signingKey)
    private val verifier  = JWT.require(algorithm).withIssuer(ISSUER).build()

    fun create(payloadClaims: Map<String, Any>): String =
        JWT.create().withIssuer(ISSUER).withPayload(payloadClaims).sign(algorithm)

    fun verify(token: String): DecodedJWT = verifier.verify(token)

    companion object {
        private const val ISSUER = "simplysend"
    }
}