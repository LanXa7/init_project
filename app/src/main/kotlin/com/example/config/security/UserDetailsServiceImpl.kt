package com.example.config.security

import com.example.exception.UserNotFoundException
import com.example.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userService: UserService
) : UserDetailsService {

    fun loadUserByUserId(userId: Long): UserDetails {
        val user = userService.queryUserWithRolePermission(userId)
            ?: throw UserNotFoundException()
        val roles = user.roles.map { it.code.serialize() }
        val permissions = user.roles.flatMap { it.permissions }.map { it.code.serialize() }.distinct()
        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles(*roles.toTypedArray())
            .authorities(*permissions.toTypedArray())
            .build()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let {
            loadUserByUserId(it.toLong())
        } ?: throw UserNotFoundException()
    }

}