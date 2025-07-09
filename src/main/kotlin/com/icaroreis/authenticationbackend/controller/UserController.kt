package com.icaroreis.authenticationbackend.controller

import com.icaroreis.authenticationbackend.controller.request.UpdateUserRequest
import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.usecases.user.GetCurrentUserUsecase
import com.icaroreis.authenticationbackend.usecases.user.GetUserUsecase
import com.icaroreis.authenticationbackend.usecases.user.UpdateUserUsecase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    val getCurrentUserUsecase: GetCurrentUserUsecase,
    val getUserUsecase: GetUserUsecase,
    val updateUserUsecase: UpdateUserUsecase
) {
    @GetMapping("/me")
    fun getCurrentUser(): UserResponse {
        return getCurrentUserUsecase.execute()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserResponse {
        return getUserUsecase.execute(id)
    }

    @PatchMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateUserRequest
    ): UserResponse {
        return updateUserUsecase.execute(
            id,
            request.username,
            request.email,
            request.firstName,
            request.lastName
        )
    }
}
