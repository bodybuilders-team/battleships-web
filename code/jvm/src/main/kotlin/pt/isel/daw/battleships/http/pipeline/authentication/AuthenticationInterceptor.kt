package pt.isel.daw.battleships.http.pipeline.authentication

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.battleships.utils.JwtProvider
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Intercepts requests that need authentication.
 *
 * The interceptor checks:
 * 1. If the request has an Authorization header
 * 2. If the token in the header is a bearer token
 * 3. If the token is valid
 *
 * @property jwtProvider the JWT provider
 */
@Component
class AuthenticationInterceptor(
    val jwtProvider: JwtProvider
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (
            handler !is HandlerMethod ||
            (
                    !handler.hasMethodAnnotation(Authenticated::class.java) &&
                            !handler.method.declaringClass.isAnnotationPresent(Authenticated::class.java)
                    )
        ) return true

        val authHeader = request.getHeader(AUTHORIZATION_HEADER)
        if (authHeader == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Token")
            return false
        }

        val token = jwtProvider.parseBearerToken(authHeader)
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token")
            return false
        }

        request.setAttribute(TOKEN_ATTRIBUTE_NAME, token)
        return true
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val TOKEN_ATTRIBUTE_NAME = "token"
    }
}
