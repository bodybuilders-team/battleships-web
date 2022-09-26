package pt.isel.daw.battleships.pipeline

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.daw.battleships.JwtUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    val jwtUtils: JwtUtils
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // TODO: Verify if this is needed
        if (handler !is HandlerMethod) {
            return true
        }

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Token")
            return false
        }

        val token = jwtUtils.parseBearerToken(authHeader)
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token")
            return false
        }

        val payload = jwtUtils.validateToken(token)

        if (payload == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
            return false
        }

        request.setAttribute("authPayload", payload)

        return true
    }
}
