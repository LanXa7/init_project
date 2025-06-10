package com.example.utils

import com.example.Const
import com.example.enums.EmailSendLimitLevel
import com.example.enums.EmailType
import com.example.exception.EmailSendLimitException
import com.example.plugin.EmailSendLimitValidator
import org.redisson.api.RLock
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class EmailUtils(
    private val rabbitTemplate: RabbitTemplate,
    private val redissonUtils: RedissonUtils,
    private val emailSendLimitValidatorRegistry: PluginRegistry<EmailSendLimitValidator, EmailSendLimitLevel>
) {

    fun getVerifyCode(
        email: String,
        type: EmailType,
        address: String
    ) = sendCodeMail(email, type, address)

    private fun sendCodeMail(email: String, type: EmailType, address: String) {
        val lock: RLock = redissonUtils.auth.getLock(address.intern())
        lock.lock()
        emailSendLimitValidatorRegistry.getPlugins()
            .forEach { validator ->
                val result = validator.validate(email, address)
                if (!result.isValid) {
                    throw EmailSendLimitException(result.errorCode)
                }
            }
        val code = Random.nextInt(899999) + 100000
        val data: Map<String, Any> = mapOf(
            "type" to type,
            "email" to email,
            "code" to code
        )
        rabbitTemplate.convertAndSend(Const.MQ_MAIL, data)
        redissonUtils.auth.setEmailVerifyCode(email, code.toString())
        lock.unlock()
    }
}