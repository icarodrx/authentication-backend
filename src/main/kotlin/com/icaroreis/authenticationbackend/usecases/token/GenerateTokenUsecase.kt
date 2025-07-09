package com.icaroreis.authenticationbackend.usecases.token

import com.icaroreis.authenticationbackend.domain.User
import com.icaroreis.authenticationbackend.security.jwt.SigningKeyProvider
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class GenerateTokenUsecase(
    @Value("\${jwt.expiration}") private val expiration: Long,
    private val signingKeyProvider: SigningKeyProvider
) {
    fun execute(user: User): String {
        return Jwts
            .builder()
            .subject(user.id.toString())
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(signingKeyProvider.execute(), Jwts.SIG.HS256)
            .compact()
    }
}
