package com.icaroreis.authenticationbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class AuthenticationBackendApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationBackendApplication>(*args)
}
