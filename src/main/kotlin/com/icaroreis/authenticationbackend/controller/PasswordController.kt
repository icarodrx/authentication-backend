package com.icaroreis.authenticationbackend.controller

import com.icaroreis.authenticationbackend.controller.request.ForgotPasswordRequest
import com.icaroreis.authenticationbackend.controller.request.ResetPasswordRequest
import com.icaroreis.authenticationbackend.controller.request.UpdatePasswordRequest
import com.icaroreis.authenticationbackend.usecases.password.ResetPasswordUsecase
import com.icaroreis.authenticationbackend.usecases.password.StartPasswordResetUsecase
import com.icaroreis.authenticationbackend.usecases.password.UpdatePasswordUsecase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/password")
class PasswordController(
    private val startPasswordResetUsecase: StartPasswordResetUsecase,
    private val resetPasswordUsecase: ResetPasswordUsecase,
    private val updatePasswordUsecase: UpdatePasswordUsecase
) {
    @PostMapping("/forgot")
    fun forgotPassword(@Valid @RequestBody forgotPasswordRequest: ForgotPasswordRequest): ResponseEntity<Void> {
        startPasswordResetUsecase.execute(forgotPasswordRequest.usernameOrEmail)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/reset")
    fun resetPassword(@Valid @RequestBody resetPasswordRequest: ResetPasswordRequest): ResponseEntity<Void> {
        resetPasswordUsecase.execute(resetPasswordRequest.token, resetPasswordRequest.newPassword)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/update")
    fun updatePassword(@Valid @RequestBody updatePasswordRequest: UpdatePasswordRequest): ResponseEntity<Void> {
        updatePasswordUsecase.execute(updatePasswordRequest.currentPassword, updatePasswordRequest.newPassword)
        return ResponseEntity.ok().build()
    }
}
