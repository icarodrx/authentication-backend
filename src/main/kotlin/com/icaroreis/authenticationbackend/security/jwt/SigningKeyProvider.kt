package com.icaroreis.authenticationbackend.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class SigningKeyProvider(
    @Value("\${jwt.secret}") private val secretKey: String
) {
    fun execute(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
