package by.trusevich.house.core.util

import org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST
import org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes
import org.springframework.web.context.request.RequestContextHolder.getRequestAttributes
import org.springframework.web.context.request.RequestContextHolder.setRequestAttributes
import org.springframework.web.context.request.ServletRequestAttributes

const val USER_PRINCIPAL = "userPrincipal"

const val IDENTITY_HEADER = "identity"

@Suppress("UNCHECKED_CAST")
fun <T>getHttpRequestAttribute(attributeName: String) =
    getRequestAttributes()?.getAttribute(attributeName, SCOPE_REQUEST) as? T

fun setHttpRequestAttribute(attributeName: String, attributeValue: Any) {
    if (attributeName.isNotBlank()) {
        currentRequestAttributes()
            .apply { setAttribute(attributeName, attributeValue, SCOPE_REQUEST) }
            .also { setRequestAttributes(it, true) }
    }
}

fun getHttpRequestUri() = getRequestAttributes()?.run { (this as ServletRequestAttributes).request.requestURI!! }
