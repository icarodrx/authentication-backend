package com.icaroreis.authenticationbackend.usecases.user

import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.exception.GetCurrentUserException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.filter.ContextHolder
import org.springframework.stereotype.Service

@Service
class GetCurrentUserUsecase(
    val contextHolder: ContextHolder,
    private val userRepository: UserRepository
) {
    fun execute(): UserResponse {
        val actingUser = contextHolder.currentUser ?: throw GetCurrentUserException("Current user data not found")
        val actingUserId = actingUser.id ?: throw GetCurrentUserException("Invalid user")
        val user = userRepository.findById(actingUserId).orElseThrow { GetCurrentUserException("User with id $actingUserId not found") }

        return UserResponse.from(user)
    }
}
