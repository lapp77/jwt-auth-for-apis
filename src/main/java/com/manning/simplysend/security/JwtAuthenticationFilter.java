package com.manning.simplysend.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLE = "role";

    private final RequestMatcher authHeaderRequestMatcher = new RequestHeaderRequestMatcher(AUTH_HEADER_NAME);
    private final JwtManager jwtManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtManager jwtManager) {
        super(authenticationManager);
        this.jwtManager = jwtManager;
        setUsernameParameter("emailId");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        if (authHeaderRequestMatcher.matches(request)) { // from JWT
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        } else { // from login attempt
            String username = authResult.getName();
            String role = authResult.getAuthorities().stream().findFirst().map(authority -> authority.toString())
                    .orElse("");
            Map<String, Object> claims = Map.of(CLAIM_USERNAME, username, CLAIM_ROLE, role);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader(AUTH_HEADER_NAME, "Bearer " + jwtManager.create(claims));
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!authHeaderRequestMatcher.matches(request)) { // is login attempt
            return super.attemptAuthentication(request, response);
        }

        try {
            String bearerToken = request.getHeader(AUTH_HEADER_NAME);
            String token = bearerToken.replaceAll("[Bb]earer\\s+", "");
            DecodedJWT decodedJWT = jwtManager.verify(token);
            String username = decodedJWT.getClaim(CLAIM_USERNAME).asString();
            String role = decodedJWT.getClaim(CLAIM_ROLE).asString();

            return new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(role)));
        } catch (JWTVerificationException ex) {
            throw new BadCredentialsException(ex.getLocalizedMessage());
        }
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response) || authHeaderRequestMatcher.matches(request);
    }
}
