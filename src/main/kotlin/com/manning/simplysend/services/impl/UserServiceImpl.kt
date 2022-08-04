package com.manning.simplysend.services.impl

import com.manning.simplysend.dto.UserDTO
import com.manning.simplysend.entities.User
import com.manning.simplysend.entities.UserCredentials
import com.manning.simplysend.exceptions.InvalidProfileException
import com.manning.simplysend.exceptions.UserAlreadyRegisteredException
import com.manning.simplysend.mappers.CredentialsMapper
import com.manning.simplysend.mappers.UserMapper
import com.manning.simplysend.repositories.UserCredentialsRepository
import com.manning.simplysend.repositories.UserRepository
import com.manning.simplysend.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.lang.NonNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.validation.Validation

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val credentialsRepository: UserCredentialsRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun createUser(@NonNull userDTO: UserDTO) {
        val user = UserMapper.fromDTO(userDTO)
        val credentials = CredentialsMapper.from(userDTO)
        val validator = Validation.buildDefaultValidatorFactory().validator
        val violations = validator.validate(userDTO)

        if (violations.isNotEmpty()) {
            throw InvalidProfileException()
        }

        val registeredUser = userRepository.findByEmail(user.email!!)

        if (registeredUser.isPresent) {
            throw UserAlreadyRegisteredException()
        }

        val manager = Optional.ofNullable(userDTO.managerID).flatMap(userRepository::findById).orElse(null)
        user.setManager(manager)
        userRepository.save(user)

        credentials.username = user.email
        credentials.password = passwordEncoder.encode(userDTO.password)
        credentials.enabled = true
        credentialsRepository.save(credentials)
    }

    override fun listUsers(page: Int, limit: Int): Page<UserDTO> {
        val pageRequest = PageRequest.of(page, limit)
        return userRepository.findAll(pageRequest).map(UserMapper::toDTO)
    }

    override fun userInfo(emailId: String): UserDTO {
        return userRepository.findByEmail(emailId).map(UserMapper::toDTO).get()
    }

    override fun revoke(id: Long) {
        userRepository.findById(id)
            .ifPresent { user: User ->
                credentialsRepository.findByUser_Email(user.email!!)
                    .ifPresent { credentials: UserCredentials ->
                        credentials.enabled = false
                        credentialsRepository.save(credentials)
                    }
            }
    }
}