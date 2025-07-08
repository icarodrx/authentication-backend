package com.icaroreis.authenticationbackend.controller.response

data class LoginResponse(
    val token: String,
    val tokenType: String? = null,
    val expiresIn: Long? = null
)
