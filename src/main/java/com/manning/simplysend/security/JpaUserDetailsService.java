package com.manning.simplysend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manning.simplysend.entities.UserCredentials;
import com.manning.simplysend.repositories.UserCredentialsRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserCredentialsRepository credentialsRepository;

    public JpaUserDetailsService(UserCredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials credentials = credentialsRepository.findByUser_Email(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.builder()
                .username(username)
                .password(credentials.getPassword())
                .roles("USER")
                .build();
    }

}
