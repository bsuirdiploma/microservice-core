package by.trusevich.house.core.service

import by.trusevich.house.core.exception.UnauthorizedException
import io.jsonwebtoken.Jwts.parser
import org.springframework.stereotype.Service

@Service
open class BaseAuthenticationService {

    @Suppress("UNCHECKED_CAST")
    fun parseToken(jwtToken: String): Map<String, String> {
        try {
            val body = parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).body

            return body as Map<String, String>
        } catch (e: Exception) {

            throw UnauthorizedException()
        }
    }

    companion object {

        const val SECRET_KEY = "hJi19anK3a"

        const val SURNAME = "surname"

        const val NAME = "name"

        const val ROLE = "role"

        const val EXPIRATION = "expiration"

    }

}