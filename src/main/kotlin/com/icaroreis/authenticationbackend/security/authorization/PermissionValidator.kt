package com.icaroreis.authenticationbackend.security.authorization

import com.icaroreis.authenticationbackend.exception.ForbiddenException
import com.icaroreis.authenticationbackend.exception.PermissionValidatorException
import com.icaroreis.authenticationbackend.security.filter.ContextHolder
import org.springframework.stereotype.Component

@Component
class PermissionValidator(
    val contextHolder: ContextHolder
) {
    fun validate(id: Long) {
        val actingUser = contextHolder.currentUser ?: throw PermissionValidatorException("Current user data not found")
        val actingUserId = actingUser.id ?: throw PermissionValidatorException("User invalid")

        if (actingUser.isAdmin() || actingUserId == id) {
            return
        }

        throw ForbiddenException()
    }
}
