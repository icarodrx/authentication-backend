package com.icaroreis.authenticationbackend.controller.response

import com.icaroreis.authenticationbackend.domain.User

data class UserResponse(
    val id: Long,
    val firstName: String,
    val lastName: String? = null,
    val username: String,
    val email: String,
    val profileName: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = requireNotNull(user.id),
                username = user.username,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                profileName = user.profile.name
            )
        }
    }
}
