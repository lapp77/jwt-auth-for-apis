package com.manning.simplysend.mappers

import com.manning.simplysend.dto.UserDTO
import com.manning.simplysend.entities.User

object UserMapper : BaseMapper() {

    fun fromDTO(userDTO: UserDTO): User {
        val u = User()
        u.firstName = userDTO.firstName
        u.lastName = userDTO.lastName
        u.address = userDTO.address
        u.age = userDTO.age
        u.email = userDTO.emailId
        u.role = userDTO.role
        u.tag = userDTO.tag
        u.phone = userDTO.phone
        return u
    }

    fun toDTO(user: User): UserDTO {
        val dto = UserDTO()
        dto.id = user.id
        dto.firstName = htmlEscape(user.firstName)
        dto.lastName = htmlEscape(user.lastName)
        dto.address = htmlEscape(user.address)
        dto.age = user.age
        dto.emailId = htmlEscape(user.email)
        dto.role = user.role
        dto.tag = htmlEscape(user.tag)
        dto.phone = htmlEscape(user.phone)
        if (user.manager != null) {
            dto.managerID = user.manager!!.id
        }
        return dto
    }
}