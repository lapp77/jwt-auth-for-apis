package com.manning.simplysend.services.impl;

import com.manning.simplysend.dto.UserDTO;
import com.manning.simplysend.entities.User;
import com.manning.simplysend.entities.UserCredentials;
import com.manning.simplysend.exceptions.InvalidProfileException;
import com.manning.simplysend.exceptions.UserAlreadyRegisteredException;
import com.manning.simplysend.mappers.CredentialsMapper;
import com.manning.simplysend.mappers.UserMapper;
import com.manning.simplysend.repositories.UserCredentialsRepository;
import com.manning.simplysend.repositories.UserRepository;
import com.manning.simplysend.services.UserService;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            UserCredentialsRepository credentialsRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(@NonNull UserDTO userDTO) {
        User user = UserMapper.fromDTO(userDTO);
        UserCredentials credentials = CredentialsMapper.from(userDTO);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        if (!violations.isEmpty()) {
            throw new InvalidProfileException();
        }

        Optional<User> registeredUser = userRepository.findByEmail(user.getEmail());

        if (registeredUser.isPresent()) {
            throw new UserAlreadyRegisteredException();
        }

        userRepository.save(user);

        credentials.setUsername(user.getEmail());
        credentials.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        credentials.setEnabled(true);
        credentialsRepository.save(credentials);
    }
}
