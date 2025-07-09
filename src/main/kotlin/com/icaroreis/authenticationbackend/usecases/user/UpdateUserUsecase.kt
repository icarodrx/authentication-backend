package com.icaroreis.authenticationbackend.usecases.user

import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.exception.UpdateUserException
import com.icaroreis.authenticationbackend.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UpdateUserUsecase(
    private val userRepository: UserRepository
) {
    @Transactional
    fun execute(userId: Long,
                username: String?,
                email: String?,
                firstName: String?,
                lastName: String?
    ): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { UpdateUserException("User with id $userId not found") }

        var updatedUser = user.copy()

        username?.let { newUsername ->
            validateUniqueUsername(newUsername, userId)
            updatedUser = updatedUser.copy(username = newUsername)
        }

        email?.let { newEmail ->
            validateUniqueEmail(newEmail, userId)
            updatedUser = updatedUser.copy(email = newEmail)
        }

        firstName?.let { newFirstName ->
            updatedUser = updatedUser.copy(firstName = newFirstName)
        }

        lastName?.let { newLastName ->
            updatedUser = updatedUser.copy(lastName = newLastName)
        }

        userRepository.save(updatedUser)

        return UserResponse.from(updatedUser)
    }

    private fun validateUniqueUsername(username: String, userId: Long) {
        if (userRepository.existsByUsernameAndIdNot(username, userId)) {
            throw UpdateUserException("The username $username is already taken")
        }
    }

    private fun validateUniqueEmail(email: String, userId: Long) {
        if (userRepository.existsByEmailAndIdNot(email, userId)) {
            throw UpdateUserException("The email $email is already taken")
        }
    }
}
