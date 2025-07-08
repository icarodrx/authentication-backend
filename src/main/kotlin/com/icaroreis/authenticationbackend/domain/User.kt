package com.icaroreis.authenticationbackend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name="users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", allocationSize = 1)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val firstName: String,

    val lastName: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val profile: ProfileEnum,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
        fun isAdmin(): Boolean {
            return this.profile == ProfileEnum.ADMIN
        }
}
