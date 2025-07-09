package com.icaroreis.authenticationbackend.security.filter

import com.icaroreis.authenticationbackend.domain.User
import com.icaroreis.authenticationbackend.exception.UnauthorizedException
import com.icaroreis.authenticationbackend.repository.UserRepository
import com.icaroreis.authenticationbackend.usecases.token.ExtractClaimsUsecase
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Date

@RequestScope
@Component
class ContextHolder {
    var currentUser: User? = null
}

@Component
class AuthFilter(private val extractClaimsUsecase: ExtractClaimsUsecase,
                 private val userRepository: UserRepository,
                 private val contextHolder: ContextHolder
) : OncePerRequestFilter() {
    private val pathMatcher = AntPathMatcher()
    private val excludedPaths = listOf(
        "/v1/register/**",
        "/v1/login/**",
        "/v1/password/forgot",
        "/v1/password/reset",
    )

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val requestURI = request.requestURI
        val isOptionsRequest = request.method == HttpMethod.OPTIONS.name()
        val isExcludedPath = excludedPaths.any { pattern ->
            pathMatcher.match(pattern, requestURI)
        }

        return isExcludedPath || isOptionsRequest
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw UnauthorizedException("Missing authorization token")
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw UnauthorizedException("Authorization token missing Bearer prefix")
        }

        val token = authHeader.substring(7)
        val claims = extractClaimsUsecase.execute(token)

        if (claims.expiration.before(Date())) {
            throw UnauthorizedException("Authorization token expired")
        }

        val userId = claims.subject.toLong()
        val user = userRepository.findById(userId)
            .orElseThrow { throw UnauthorizedException("The user id $userId provided by the authorization token was not found") }
        contextHolder.currentUser = user
        chain.doFilter(request, response)
    }
}
