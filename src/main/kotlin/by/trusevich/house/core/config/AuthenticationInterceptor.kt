package by.trusevich.house.core.config

import by.trusevich.house.core.exception.UnauthorizedException
import by.trusevich.house.core.service.BaseAuthenticationService
import by.trusevich.house.core.service.BaseAuthenticationService.Companion.EXPIRATION
import by.trusevich.house.core.service.BaseAuthenticationService.Companion.NAME
import by.trusevich.house.core.service.BaseAuthenticationService.Companion.SURNAME
import by.trusevich.house.core.util.TOKEN_HEADER
import by.trusevich.house.core.util.USER_PRINCIPAL
import by.trusevich.house.core.util.setHttpRequestAttribute
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.util.Date
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Suppress("unused")
open class AuthenticationInterceptor(private val baseAuthenticationService: BaseAuthenticationService) :
    HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == OPTIONS.name)
            return true

        request.getHeader(TOKEN_HEADER)?.let { token ->
            val userDetails = baseAuthenticationService.parseToken(token)

            setHttpRequestAttribute(USER_PRINCIPAL, "${userDetails[NAME]} ${userDetails[SURNAME]}")

            if (userDetails[EXPIRATION]?.toLong()?.let { Date(it).before(Date()) } != false)
                throw UnauthorizedException()

            return true
        } ?: throw UnauthorizedException()
    }
}
