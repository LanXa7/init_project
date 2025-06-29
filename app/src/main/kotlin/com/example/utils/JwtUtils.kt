package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtUtils(
    @param:Value($$"${security.jwt.secret}")
    private val secret: String,
    @param:Value($$"${security.jwt.expire}")
    private val expire: Long,
    private val redissonUtils: RedissonUtils,
) {

    fun create(userIdentify: String): String =
        JWT.create()
            .withSubject(userIdentify)
            .withJWTId(UUID.randomUUID().toString())
            .withIssuedAt(
                Date()
            )
            .withExpiresAt(
                Date.from(
                    LocalDateTime.now().plusHours(expire)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                )
            )
            .sign(Algorithm.HMAC256(secret))

    private fun getVerifier(): JWTVerifier {
        val algorithm = Algorithm.HMAC256(secret)
        return JWT.require(algorithm).build()
    }

    fun verify(token: String): Boolean {
        val verifier = this.getVerifier()
        try {
            val verify = verifier.verify(token)
            if (redissonUtils.jwt.isInvalidToken(verify.id)) {
                return false
            }
            val claims = verify.claims
            return !Date().after(claims["exp"]?.asDate())
        } catch (ex: JWTVerificationException) {
            log.error { "JWT verification failed" }
            return false
        }
    }

    fun getSubject(token: String): String =
        JWT.decode(token).subject

    fun extract(authorization: String?): String? =
        authorization?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")

    fun makeToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        userIdentify: String
    ) =
        response.addHeader("Authorization", "Bearer ${create(userIdentify)}")

    private fun invalidateJwt(authorization: String?): Boolean {
        val token = this.extract(authorization)
        val verifier = this.getVerifier()
        try {
            val verify = verifier.verify(token)
            return deleteToken(verify.id, verify.expiresAt)
        } catch (ex: JWTVerificationException) {
            return false
        }
    }

    private fun deleteToken(
        uuid: String,
        time: Date
    ): Boolean {
        if (redissonUtils.jwt.isInvalidToken(uuid)) {
            return false
        }
        val now = Date()
        val expire = (time.time - now.time).coerceAtLeast(0)
        redissonUtils.jwt.addBlacklist(uuid, expire)
        return true
    }

    fun removeToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        if (this.invalidateJwt(request.getHeader("Authorization"))) {
            response.addHeader("Authorization", null)
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}