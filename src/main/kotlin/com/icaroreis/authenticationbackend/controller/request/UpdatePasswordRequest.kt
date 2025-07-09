package com.icaroreis.authenticationbackend.controller.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:NotBlank(message = "Current password cannot be empty")
    val currentPassword: String,

    @field:NotBlank(message = "New password cannot be empty")
    @field:Size(min = 8, message = "New password must be at least 8 characters long")
    @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).*$",
        message = "New password must contain at least one digit, one lowercase, one uppercase, and one special character")
    val newPassword: String
)
