package com.icaroreis.authenticationbackend.security.encryptor

interface PasswordEncryptor {
    fun encode(rawPassword: CharSequence): String
    fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean
}
