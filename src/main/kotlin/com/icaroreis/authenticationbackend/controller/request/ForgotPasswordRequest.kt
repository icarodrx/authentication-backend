package com.icaroreis.authenticationbackend.controller.request

import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequest(
    @field:NotBlank(message = "Username or email cannot be empty")
    val usernameOrEmail: String
)
