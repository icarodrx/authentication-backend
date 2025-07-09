package com.icaroreis.authenticationbackend.usecases.password

import com.icaroreis.authenticationbackend.domain.PasswordReset
import com.icaroreis.authenticationbackend.repository.PasswordResetRepository
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.usecases.email.SendPasswordResetEmailUsecase
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Base64
import java.util.UUID

@Service
class StartPasswordResetUsecase(
    private val sendPasswordResetEmailUsecase: SendPasswordResetEmailUsecase,
    private val userRepository: UserRepository,
    private val passwordResetRepository: PasswordResetRepository
) {
    @Transactional
    fun execute(usernameOrEmail: String) {
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null)

        if (user != null) {
            val existingPasswordReset = user.id?.let { userId ->
                passwordResetRepository.findByUserId(userId).orElse(null)
            }

            val token = "${user.id}:${UUID.randomUUID()}"
            val encodedToken = Base64.getEncoder().encodeToString(token.toByteArray(Charsets.UTF_8))
            val expiration = LocalDateTime.now().plusHours(1)

            val passwordReset: PasswordReset

            if (existingPasswordReset != null) {
                existingPasswordReset.token = encodedToken
                existingPasswordReset.expiration = expiration
                existingPasswordReset.used = false
                passwordReset = existingPasswordReset
            } else {
                passwordReset = PasswordReset(
                    token = encodedToken,
                    user = user,
                    expiration = expiration,
                    used = false
                )
            }

            passwordResetRepository.save(passwordReset)
            sendPasswordResetEmailUsecase.execute(user.email, encodedToken)
        }
    }
}
