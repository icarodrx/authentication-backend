package com.icaroreis.authenticationbackend.usecases.password

import com.icaroreis.authenticationbackend.exception.UpdatePasswordException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.encryptor.PasswordEncryptor
import com.icaroreis.authenticationbackend.security.filter.ContextHolder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UpdatePasswordUsecase(
    private val userRepository: UserRepository,
    private val passwordEncryptor: PasswordEncryptor,
    private val contextHolder: ContextHolder
) {
    @Transactional
    fun execute(currentPassword: String, newPassword: String) {
        val actingUser = contextHolder.currentUser ?: throw UpdatePasswordException("Current user data not found")

        if (!passwordEncryptor.matches(currentPassword, actingUser.password)) {
            throw UpdatePasswordException("The current password provided is not correct")
        }

        val newHashedPassword = passwordEncryptor.encode(newPassword)

        val updatedUser = actingUser.copy(
            password = newHashedPassword,
            updatedAt = LocalDateTime.now()
        )

        userRepository.save(updatedUser)
    }
}
