package com.manning.simplysend.mappers

import com.manning.simplysend.dto.UserDTO
import com.manning.simplysend.entities.UserCredentials

object CredentialsMapper {
    
    fun from(userDTO: UserDTO): UserCredentials {
        val uc = UserCredentials()
        uc.password = userDTO.password
        return uc
    }
}