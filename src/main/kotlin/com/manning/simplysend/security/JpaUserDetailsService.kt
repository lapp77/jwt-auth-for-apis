package com.manning.simplysend.security

import com.manning.simplysend.repositories.UserCredentialsRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JpaUserDetailsService(private val credentialsRepository: UserCredentialsRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val credentials = credentialsRepository.findByUser_Email(username)
            .orElseThrow { UsernameNotFoundException(username) }

        if (!credentials.enabled!!) {
            throw UsernameNotFoundException("User is not enabled")
        }

        val userBuilder = User.builder().username(username).password(credentials.password)

        if (credentials.user!!.role != null) {
            userBuilder.roles(credentials.user!!.role.toString())
        }

        return userBuilder.build()
    }
}