package pt.isel.daw.battleships.http.pipeline.authentication

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.battleships.http.media.Problem
import pt.isel.daw.battleships.http.pipeline.ExceptionHandler
import pt.isel.daw.battleships.utils.JwtProvider
import java.net.URI
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
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = Problem.PROBLEM_MEDIA_TYPE
            response.writer.write(
                mapper.writeValueAsString(
                    Problem(
                        type = URI.create(ExceptionHandler.PROBLEMS_DOCS_URI + "authentication"),
                        title = "Missing Token",
                        status = HttpStatus.UNAUTHORIZED.value()
                    )
                )
            )

            return false
        }

        val token = jwtProvider.parseBearerToken(authHeader)
        if (token == null) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = Problem.PROBLEM_MEDIA_TYPE
            response.writer.write(
                mapper.writeValueAsString(
                    Problem(
                        type = URI.create(ExceptionHandler.PROBLEMS_DOCS_URI + "authentication"),
                        title = "Token is not a Bearer Token",
                        status = HttpStatus.UNAUTHORIZED.value()
                    )
                )
            )

            return false
        }

        request.setAttribute(TOKEN_ATTRIBUTE_NAME, token)
        return true
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val TOKEN_ATTRIBUTE_NAME = "token"

        val mapper = ObjectMapper().also { it.setSerializationInclusion(JsonInclude.Include.NON_NULL) }
    }
}
