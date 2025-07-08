package com.icaroreis.authenticationbackend.controller

import com.icaroreis.authenticationbackend.controller.request.RegisterRequest
import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.usecases.user.CreateUserUsecase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/register")
class RegisterController(
    private val createUserUsecase: CreateUserUsecase
) {
    @PostMapping
    fun registerUser(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
        val user = createUserUsecase.execute(
            request.username,
            request.email,
            request.password,
            request.firstName,
            request.lastName
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }
}