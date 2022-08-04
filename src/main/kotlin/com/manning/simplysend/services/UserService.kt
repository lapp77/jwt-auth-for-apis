package com.manning.simplysend.services

import com.manning.simplysend.dto.UserDTO
import org.springframework.data.domain.Page

interface UserService {

    fun createUser(userDTO: UserDTO)

    fun listUsers(page: Int, limit: Int): Page<UserDTO>

    fun userInfo(emailId: String): UserDTO

    fun revoke(id: Long)
}