package com.rdktechnologies.skit.security

import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.Serializable
import org.springframework.security.core.AuthenticationException


@Component
class UnauthorizedEntryPoint : AuthenticationEntryPoint, Serializable {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType = "application/json"
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.outputStream?.println("{\"error\":true,\"message\":\"invalid token\",\"statusCode\":401,\"data\":null}")
    }
}