package com.example.http.request

import com.example.enums.EmailType
import jakarta.validation.constraints.Email

data class EmailCodeRequest(
    @field:Email
    val email: String,
    val type: EmailType
)