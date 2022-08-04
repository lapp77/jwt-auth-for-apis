package com.manning.simplysend.repositories

import com.manning.simplysend.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface UserRepository : PagingAndSortingRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>
}