package com.icaroreis.authenticationbackend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table
data class PasswordReset(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_seq_generator")
    @SequenceGenerator(name = "password_reset_seq_generator", allocationSize = 1)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var token: String,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @Column(nullable = false)
    var expiration: LocalDateTime,

    @Column(nullable = false)
    var used: Boolean = false,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
