package com.example.service

import com.example.entity.user.User
import com.example.entity.user.addBy
import com.example.entity.user.by
import com.example.entity.user.dto.UserSignInInput
import com.example.entity.user.dto.UserSignUpInput
import com.example.enums.ERole
import com.example.enums.EmailType
import com.example.exception.*
import com.example.ext.withTransaction
import com.example.repository.UserRepository
import com.example.utils.EmailUtils
import com.example.utils.IpUtils
import com.example.utils.RedissonUtils
import jakarta.servlet.http.HttpServletRequest
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val emailUtils: EmailUtils,
    private val redissonUtils: RedissonUtils,
    private val passwordEncoder: PasswordEncoder
) {

    fun signIn(input: UserSignInInput): Long {
        val user = userRepository.findOneOrNullByText(
            input.text,
            USER_SIGN_IN_FETCHER
        ) ?: throw UserNotFoundException()
        if (passwordEncoder.matches(input.password, user.password)) {
            throw UsernameOrPasswordErrorException()
        }
        if (!user.enable) {
            throw UserIsDisabledException()
        }
        return user.id
    }

    fun signUp(input: UserSignUpInput) {
        if (userRepository.isEmailOrUserNameExists(input.email, input.username)) {
            throw UsernameOrEmailAlreadyExistsException()
        }
        val code = redissonUtils.auth.getEmailVerifyCode(input.email)
            ?: throw CodeIsNotExistsException()
        if (code != input.code) {
            throw CodeIsNotTrueException()
        }
        withTransaction {
            userRepository.save(
                User {
                    this.password = passwordEncoder.encode(input.password)
                    this.roles().addBy {
                        this.name = ERole.GENERAL
                        this.code = ERole.GENERAL
                    }
                }
            )
        }
    }

    fun getEmailVerifyCode(
        email: String,
        type: EmailType,
        request: HttpServletRequest
    ) {
        if (userRepository.isEmailOrUserNameExists(email) && type != EmailType.RESET) {
            throw UsernameOrEmailAlreadyExistsException()
        }
        emailUtils.getVerifyCode(email, type, IpUtils.getClientIp(request))
    }

    companion object {
        private val USER_SIGN_IN_FETCHER = newFetcher(User::class).by {
            password()
            enable()
        }
    }

}