package com.icaroreis.authenticationbackend.usecases.token

import com.icaroreis.authenticationbackend.security.jwt.SigningKeyProvider
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service

@Service
class ExtractClaimsUsecase(
   private val signingKeyProvider: SigningKeyProvider
) {
    fun execute(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(signingKeyProvider.execute())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
