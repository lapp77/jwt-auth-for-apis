package com.manning.simplysend.repositories

import com.manning.simplysend.entities.UserCredentials
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserCredentialsRepository : CrudRepository<UserCredentials, Long> {

    fun findByUser_Email(email: String): Optional<UserCredentials>
}