package com.icaroreis.authenticationbackend.controller.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Username or email cannot be empty")
    val usernameOrEmail: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
)
