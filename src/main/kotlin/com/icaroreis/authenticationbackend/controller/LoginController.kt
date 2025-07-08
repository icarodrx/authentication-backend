package com.icaroreis.authenticationbackend.controller

import com.icaroreis.authenticationbackend.controller.request.LoginRequest
import com.icaroreis.authenticationbackend.controller.response.LoginResponse
import com.icaroreis.authenticationbackend.usecases.login.LoginUserUsecase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/login")
class LoginController(
    private val loginUserUsecase: LoginUserUsecase
) {
    @PostMapping
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val loginResponse = loginUserUsecase.execute(loginRequest.usernameOrEmail, loginRequest.password)
        return ResponseEntity.ok(loginResponse)
    }
}
