package com.manning.simplysend.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTManager jwtManager;

    public JWTAuthenticationSuccessHandler(JWTManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        Map<String, Object> claims = Map.of("username", authentication.getName());
        String jwt = jwtManager.create(claims);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Authorization", jwt);
    }

}
