package com.manning.simplysend.repositories;

import com.manning.simplysend.entities.UserCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUser_Email(String email);
}
