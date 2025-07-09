package com.icaroreis.authenticationbackend.usecases.user

import com.icaroreis.authenticationbackend.controller.response.UserResponse
import com.icaroreis.authenticationbackend.exception.GetUserException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.security.authorization.PermissionValidator
import org.springframework.stereotype.Service

@Service
class GetUserUsecase(
    val permissionValidator: PermissionValidator,
    val userRepository: UserRepository
) {
    fun execute(id: Long): UserResponse {
        permissionValidator.validate(id)
        val user = userRepository.findById(id).orElseThrow { GetUserException("User with id $id not found") }
        return UserResponse.from(user)
    }
}
