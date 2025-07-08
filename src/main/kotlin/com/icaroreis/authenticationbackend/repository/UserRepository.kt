package com.icaroreis.authenticationbackend.repository

import com.icaroreis.authenticationbackend.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByUsernameOrEmail(username: String, email: String): Optional<User>
//    fun existsByUsernameAndIdNot(username: String, id: Long): Boolean
//    fun existsByEmailAndIdNot(email: String, id: Long): Boolean
}
