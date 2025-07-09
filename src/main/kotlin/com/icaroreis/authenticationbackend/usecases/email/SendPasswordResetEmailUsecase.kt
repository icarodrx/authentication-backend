package com.icaroreis.authenticationbackend.usecases.email

import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Service
class SendPasswordResetEmailUsecase(
    private val mailSender: JavaMailSender,
    private val resourceLoader: ResourceLoader,
    @Value("\${spring.mail.username}") private val sender: String,
    @Value("\${app.frontend.url}") private val frontendBaseUrl: String
) {
    fun execute(recipient: String, token: String) {
        val logger = LoggerFactory.getLogger(this::class.java)

        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true, "UTF-8")

            helper.setFrom(sender)
            helper.setTo(recipient)
            helper.setSubject("Password Reset Request")

            val resetUrl = "$frontendBaseUrl/reset-password?token=$token"
            val resource = resourceLoader.getResource("classpath:templates/ResetPasswordEmail.html")

            if (!resource.exists()) {
                throw FileNotFoundException("Email template not found at: $resource")
            }

            val htmlTemplate: String =
                BufferedReader(
                    InputStreamReader(
                        resource.inputStream,
                        StandardCharsets.UTF_8
                    )
                ).use { it.readText() }

            val currentYear = java.time.Year.now().value.toString()
            val emailBody = htmlTemplate
                .replace("[[resetUrl]]", resetUrl)
                .replace("[[currentYear]]", currentYear)

            helper.setText(emailBody, true)
            mailSender.send(message)
            logger.info("Password reset email sent successfully to $recipient")
        } catch (e: MailException) {
            logger.error("Failed to send password reset email to $recipient: ${e.message}", e)
        } catch (e: RuntimeException) {
            logger.error("An unexpected error occurred during password reset email process: ${e.message}", e)
        }
    }
}