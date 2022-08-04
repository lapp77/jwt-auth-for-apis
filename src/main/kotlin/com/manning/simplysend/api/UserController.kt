package com.manning.simplysend.api

import com.manning.simplysend.dto.UserDTO
import com.manning.simplysend.exceptions.InvalidProfileException
import com.manning.simplysend.exceptions.UserAlreadyRegisteredException
import com.manning.simplysend.services.UserService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(path = ["/v1/users"])
class UserController(private val usersService: UserService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listUsers(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int
    ): Page<UserDTO> {
        return try {
            usersService.listUsers(page, limit)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }
    }

    @GetMapping(value = ["/me"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun userInfo(@AuthenticationPrincipal userEmailId: String): UserDTO {
        return try {
            usersService.userInfo(userEmailId)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createUser(@RequestBody userDTO: UserDTO) {
        try {
            usersService.createUser(userDTO)
        } catch (e: UserAlreadyRegisteredException) {
            throw ResponseStatusException(HttpStatus.CONFLICT)
        } catch (e: InvalidProfileException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.localizedMessage)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }
    }

    @PostMapping(value = ["/{userId}/revoke"])
    @ResponseStatus(code = HttpStatus.OK)
    fun revoke(@PathVariable("userId") userID: Long) {
        try {
            usersService.revoke(userID)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }
    }
}