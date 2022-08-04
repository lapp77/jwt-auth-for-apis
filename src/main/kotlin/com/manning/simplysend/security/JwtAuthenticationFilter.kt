package com.manning.simplysend.security

import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(authenticationManager: AuthenticationManager, private val jwtManager: JwtManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    private val authHeaderRequestMatcher: RequestMatcher = RequestHeaderRequestMatcher(AUTH_HEADER_NAME)

    init {
        usernameParameter = "emailId"
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain,
        authResult: Authentication
    ) {
        if (authHeaderRequestMatcher.matches(request)) { // from JWT
            SecurityContextHolder.getContext().authentication = authResult
            chain.doFilter(request, response)
        } else { // from login attempt
            val username = authResult.name
            val role = authResult.authorities.stream().findFirst().map { it.toString() }.orElse("")
            val claims = mapOf(CLAIM_USERNAME to username, CLAIM_ROLE to role)
            response.status = HttpServletResponse.SC_OK
            response.setHeader(AUTH_HEADER_NAME, "Bearer " + jwtManager.create(claims))
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        response.sendError(HttpStatus.UNAUTHORIZED.value())
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication =
        if (!authHeaderRequestMatcher.matches(request)) { // is login attempt
            super.attemptAuthentication(request, response)
        } else try {
            val bearerToken = request.getHeader(AUTH_HEADER_NAME)
            val token = bearerToken.replace("[Bb]earer\\s+".toRegex(), "")
            val decodedJWT = jwtManager.verify(token)
            val username = decodedJWT.getClaim(CLAIM_USERNAME).asString()
            val role = Optional.ofNullable(decodedJWT.getClaim(CLAIM_ROLE).asString()).orElse("ANON")
            UsernamePasswordAuthenticationToken(username, null, listOf(SimpleGrantedAuthority(role)))
        } catch (ex: JWTVerificationException) {
            throw BadCredentialsException(ex.localizedMessage)
        }

    override fun requiresAuthentication(request: HttpServletRequest, response: HttpServletResponse): Boolean =
        super.requiresAuthentication(request, response) || authHeaderRequestMatcher.matches(request)

    companion object {
        private const val AUTH_HEADER_NAME = "Authorization"
        private const val CLAIM_USERNAME = "username"
        private const val CLAIM_ROLE = "role"
    }
}