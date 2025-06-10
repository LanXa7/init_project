package com.example.plugin

import com.example.enums.EmailSendLimitLevel
import com.example.exception.ErrorCode
import com.example.utils.RedissonUtils
import org.springframework.core.annotation.Order
import org.springframework.plugin.core.Plugin
import org.springframework.stereotype.Component

interface EmailSendLimitValidator : Plugin<EmailSendLimitLevel> {
    fun validate(email: String, address: String): ValidatorResult
}

data class ValidatorResult(
    val isValid: Boolean,
    val errorCode: ErrorCode,
)

@Component
@Order(0)
class EmailHourValidator(
    private val redissonUtils: RedissonUtils
) : EmailSendLimitValidator {
    override fun supports(delimiter: EmailSendLimitLevel): Boolean {
        return delimiter == EmailSendLimitLevel.EMAIL_HOUR
    }

    override fun validate(email: String, address: String): ValidatorResult {
        val isValid = redissonUtils.auth.tryAcquireCaptchaHourRateLimiter(email)
        return ValidatorResult(isValid, ErrorCode.GET_EMAIL_MANY_ONE_MINUTE)
    }
}

@Component
@Order(1)
class IpHourValidator(
    private val redissonUtils: RedissonUtils
) : EmailSendLimitValidator {
    override fun supports(delimiter: EmailSendLimitLevel): Boolean {
        return delimiter == EmailSendLimitLevel.IP_HOUR
    }

    override fun validate(email: String, address: String): ValidatorResult {
        val isValid = redissonUtils.auth.tryAcquireCaptchaHourRateLimiter(address, asIp = true)
        return ValidatorResult(isValid, ErrorCode.GET_EMAIL_MANY_ONE_MINUTE)
    }
}

@Component
@Order(2)
class EmailMinuteValidator(
    private val redissonUtils: RedissonUtils
) : EmailSendLimitValidator {
    override fun supports(delimiter: EmailSendLimitLevel): Boolean {
        return delimiter == EmailSendLimitLevel.EMAIL_MINUTE
    }

    override fun validate(email: String, address: String): ValidatorResult {
        val isValid = redissonUtils.auth.addCaptchaMinuteAtomicLong(email) == 0
        return ValidatorResult(isValid, ErrorCode.GET_EMAIL_MANY_ONE_HOUR)
    }
}

@Component
@Order(3)
class IpMinuteValidator(
    private val redissonUtils: RedissonUtils
) : EmailSendLimitValidator {
    override fun supports(delimiter: EmailSendLimitLevel): Boolean {
        return delimiter == EmailSendLimitLevel.IP_MINUTE
    }

    override fun validate(email: String, address: String): ValidatorResult {
        val isValid = redissonUtils.auth.addCaptchaMinuteAtomicLong(address, asIp = true) == 0
        return ValidatorResult(isValid, ErrorCode.GET_EMAIL_MANY_ONE_HOUR)
    }
}