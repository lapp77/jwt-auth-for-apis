package com.manning.simplysend.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.manning.simplysend.entities.User.RoleEnum
import com.manning.simplysend.validation.Password
import javax.validation.constraints.Email

class UserDTO {
    @JsonProperty("id")
    var id: Long? = null

    @JsonProperty("firstName")
    var firstName: String? = null

    @JsonProperty("lastName")
    var lastName: String? = null

    @JsonProperty("emailId")
    var emailId: @Email String? = null

    @JsonProperty("age")
    var age: Long? = null

    @JsonProperty("phone")
    var phone: String? = null

    @JsonProperty("role")
    var role: RoleEnum? = null

    @JsonProperty("managerId")
    var managerID: Long? = null

    @JsonProperty("address")
    var address: String? = null

    @JsonProperty("tag")
    var tag: String? = null

    @Password
    var password: String? = null
}