package com.example.utils

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder

object StpUtils {

    fun getCurrentUserId() =
        SecurityContextHolder.getContext().authentication?.principal as? Long
            ?: throw AccessDeniedException("User not authenticated")

}