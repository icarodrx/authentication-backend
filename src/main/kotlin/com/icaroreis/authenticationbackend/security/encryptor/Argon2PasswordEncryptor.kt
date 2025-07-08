package com.icaroreis.authenticationbackend.security.encryptor

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Argon2PasswordEncryptor : PasswordEncryptor {
    private val argon2PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    override fun encode(rawPassword: CharSequence): String {
        return argon2PasswordEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return argon2PasswordEncoder.matches(rawPassword, encodedPassword)
    }
}
