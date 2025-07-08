package com.icaroreis.authenticationbackend.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "Username cannot be empty")
    @field:Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @field:Pattern(regexp = "^[a-z0-9]+$", message = "Username must be lowercase and contain only alphanumeric characters")
    val username: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
        message = "Password must contain at least one digit and one character"
    )
    val password: String,

    @field:NotBlank(message = "First name cannot be empty")
    val firstName: String,

    val lastName: String
)
