package com.icaroreis.authenticationbackend.usecases.login

import com.icaroreis.authenticationbackend.controller.response.LoginResponse
import com.icaroreis.authenticationbackend.exception.LoginException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.encryptor.PasswordEncryptor
import com.icaroreis.authenticationbackend.usecases.token.GenerateTokenUsecase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LoginUserUsecase(
    private val userRepository: UserRepository,
    private val passwordEncryptor: PasswordEncryptor,
    private val generateTokenUsecase: GenerateTokenUsecase,
    @Value("\${jwt.type}") private val tokenType: String,
    @Value("\${jwt.expiration}") private val tokenExpiration: Long
) {
    fun execute(usernameOrEmail: String, password: String): LoginResponse {
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow { LoginException("Invalid credentials") }

        if (!passwordEncryptor.matches(password, user.password)) {
            throw LoginException("Invalid credentials")
        }

        val jwtToken = generateTokenUsecase.execute(user)

        return LoginResponse(jwtToken, tokenType, tokenExpiration)
    }
}
