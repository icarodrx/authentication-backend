package com.icaroreis.authenticationbackend.repository

import com.icaroreis.authenticationbackend.domain.PasswordReset
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PasswordResetRepository : JpaRepository<PasswordReset, Long> {
    fun findByUserId(userId: Long): Optional<PasswordReset>
}
