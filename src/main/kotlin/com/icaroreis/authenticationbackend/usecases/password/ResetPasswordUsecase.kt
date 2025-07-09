package com.icaroreis.authenticationbackend.usecases.password

import com.icaroreis.authenticationbackend.domain.User
import com.icaroreis.authenticationbackend.exception.PasswordResetException
import com.icaroreis.authenticationbackend.repository.PasswordResetRepository
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.encryptor.PasswordEncryptor
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Base64

@Service
class ResetPasswordUsecase(
    private val passwordResetRepository: PasswordResetRepository,
    private val userRepository: UserRepository,
    private val passwordEncryptor: PasswordEncryptor
) {
    @Transactional
    fun execute(token: String, newPassword: String): User {
        val decodedToken = String(Base64.getDecoder().decode(token), Charsets.UTF_8)
        val userId = decodedToken.split(":")[0]

        val resetToken = passwordResetRepository.findByUserId(userId.toLong())
            .orElseThrow { PasswordResetException("Password reset token not found") }

        if (resetToken.token != token) {
            throw PasswordResetException("Password reset token is not valid")
        }

        if (resetToken.used) {
            throw PasswordResetException("Password reset token already used")
        }

        if (resetToken.expiration.isBefore(LocalDateTime.now())) {
            throw PasswordResetException("Password reset token expired")
        }

        val user = resetToken.user
        val newPasswordHashed = passwordEncryptor.encode(newPassword)

        userRepository.save(user.copy(password = newPasswordHashed))
        passwordResetRepository.save(resetToken.copy(used = true))

        return user
    }
}