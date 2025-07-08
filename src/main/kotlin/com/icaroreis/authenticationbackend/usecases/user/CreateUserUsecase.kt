package com.icaroreis.authenticationbackend.usecases.user

import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.domain.ProfileEnum
import com.icaroreis.authenticationbackend.domain.User
import com.icaroreis.authenticationbackend.exception.CreateUserException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.encryptor.PasswordEncryptor
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateUserUsecase(
    private val userRepository: UserRepository,
    private val passwordEncryptor: PasswordEncryptor
) {
    @Transactional
    fun execute(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): UserResponse {
        if (userRepository.existsByUsername(username)) {
            throw CreateUserException("User with username $username already exists")
        }
        if (userRepository.existsByEmail(email)) {
            throw CreateUserException("User with email $email already exists")
        }

        val encryptedPassword = passwordEncryptor.encode(password)

        val user = User(
            username = username,
            email = email,
            password = encryptedPassword,
            firstName = firstName,
            lastName = lastName,
            profile = ProfileEnum.USER
        )

        userRepository.save(user)

        return UserResponse.from(user)
    }
}
