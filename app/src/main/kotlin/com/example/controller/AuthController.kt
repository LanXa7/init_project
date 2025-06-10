package com.example.controller

import com.example.entity.user.dto.UserSignInInput
import com.example.entity.user.dto.UserSignUpInput
import com.example.http.request.EmailCodeRequest
import com.example.service.AuthService
import com.example.utils.JwtUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/auth")
class AuthController(
    private val jwtUtils: JwtUtils,
    private val authService: AuthService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign_in")
    fun signIn(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody @Valid input: UserSignInInput
    ) =
        jwtUtils.makeToken(
            request,
            response,
            authService.signIn(input).toString()
        )

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody @Valid input: UserSignUpInput
    ) =
        authService.signUp(input)

    @PostMapping("/code")
    fun queryCode(
        @RequestBody @Valid req: EmailCodeRequest,
        request: HttpServletRequest
    ) =
        authService.getEmailVerifyCode(req.email, req.type, request)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/sing_out")
    fun signOut(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) =
        jwtUtils.removeToken(request, response)

}