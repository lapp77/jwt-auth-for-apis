package com.manning.simplysend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class JwtSecurityConfiguration(private val jwtManager: JwtManager) : WebSecurityConfigurerAdapter(true) {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter::class.java)

        http {
            authorizeHttpRequests {
                authorize("/error", permitAll)
                authorize(HttpMethod.POST, "/v1/users", permitAll)
                authorize(HttpMethod.GET, "/v1/users", hasRole("ADMIN"))
                authorize(HttpMethod.POST, "/v1/users/*/revoke", hasRole("ADMIN"))
                authorize(HttpMethod.GET, "/v1/orders", hasRole("MGR"))
                authorize(HttpMethod.POST, "/v1/orders/**", hasRole("MGR"))
                authorize(anyRequest, authenticated)
            }
            anonymous {}
            exceptionHandling {
                authenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            cors {
                configurationSource = corsConfigurationSource()
            }
        }
    }

    @Throws(Exception::class)
    private fun jwtAuthenticationFilter(): JwtAuthenticationFilter =
        JwtAuthenticationFilter(authenticationManager(), jwtManager)

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.addAllowedOrigin("localhost")
        corsConfig.allowedMethods = listOf(HttpMethod.GET.name, HttpMethod.POST.name, HttpMethod.DELETE.name)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}