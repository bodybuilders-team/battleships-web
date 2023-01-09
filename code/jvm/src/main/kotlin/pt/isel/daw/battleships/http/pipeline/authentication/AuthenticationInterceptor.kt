package pt.isel.daw.battleships.http.pipeline.authentication

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
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

        val accessTokenAuthCookie = request.cookies?.firstOrNull { it.name == ACCESS_TOKEN_COOKIE_NAME }?.value
        val refreshToken = request.cookies?.firstOrNull { it.name == REFRESH_TOKEN_COOKIE_NAME }?.value

        val accessToken = accessTokenAuthCookie
            ?: (
                jwtProvider.parseBearerToken(
                    request.getHeader(AUTHORIZATION_HEADER)
                        ?: throw AuthenticationException("Missing authorization token")
                ) ?: throw AuthenticationException("Token is not a Bearer Token")
                )

        request.setAttribute(JwtProvider.ACCESS_TOKEN_ATTRIBUTE, accessToken)

        if (refreshToken != null)
            request.setAttribute(JwtProvider.REFRESH_TOKEN_ATTRIBUTE, refreshToken)

        return true
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val ACCESS_TOKEN_COOKIE_NAME = "access_token"
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh_token"
    }
}
