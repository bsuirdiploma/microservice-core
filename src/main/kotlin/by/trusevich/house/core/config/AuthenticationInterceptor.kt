package by.trusevich.house.core.config

import by.trusevich.house.core.util.IDENTITY_HEADER
import by.trusevich.house.core.util.USER_PRINCIPAL
import by.trusevich.house.core.util.setHttpRequestAttribute
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Suppress("unused")
open class AuthenticationInterceptor : HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any) =
        request.getHeader(IDENTITY_HEADER).let { identitty ->
            setHttpRequestAttribute(USER_PRINCIPAL, identitty)

            //todo validate user access

            true
        }
}