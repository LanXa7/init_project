package com.example.config.security

import com.example.config.properties.SecurityProperties
import com.example.utils.JwtUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val securityProperties: SecurityProperties,
    private val jwtUtils: JwtUtils,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) : OncePerRequestFilter() {

    private val bypassMatcher: RequestMatcher = run {
        val paths = securityProperties.exposedEndpoints
        val matchers = paths.map { endpoint ->
            if (endpoint.method == null) {
                // 如果 method 为 null,匹配所有方法
                PathPatternRequestMatcher
                    .withDefaults()
                    .matcher(endpoint.path)
            } else {
                // 如果指定了 method,使用支持方法的 Matcher
                PathPatternRequestMatcher
                    .withDefaults()
                    .matcher(endpoint.method, endpoint.path)
            }
        }
        OrRequestMatcher(matchers)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (bypassMatcher.matches(request)) {
            filterChain.doFilter(request, response)
            return
        }
        val authorization = request.getHeader("Authorization")
        val token = jwtUtils.extract(authorization)
        if (token != null && jwtUtils.verify(token)) {
            try {
                val userDetails = userDetailsServiceImpl.loadUserByUsername(jwtUtils.getSubject(token))
                val authenticated = JwtAuthenticationToken.authenticated(userDetails, token, userDetails.authorities)
                authenticated.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticated
            } catch (ex: Exception) {
                log.error(ex) { "jwt with invalid user id: ${jwtUtils.getSubject(token)}" }
            }
        }
        filterChain.doFilter(request, response)
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}