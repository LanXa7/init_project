package com.example.config

import com.example.plugin.EmailSendLimitValidator
import org.springframework.context.annotation.Configuration
import org.springframework.plugin.core.config.EnablePluginRegistries

@Configuration
@EnablePluginRegistries(
    EmailSendLimitValidator::class,
)
class SpringPluginConfig {

}