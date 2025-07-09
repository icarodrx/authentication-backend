package com.icaroreis.authenticationbackend.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    @field:Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @field:Pattern(regexp = "^[a-z0-9]+$", message = "Username must be lowercase and contain only alphanumeric characters")
    val username: String? = null,

    @field:Email(message = "Invalid email format")
    val email: String? = null,

    val firstName: String? = null,

    val lastName: String? = null
)
