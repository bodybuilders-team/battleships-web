package pt.isel.daw.battleships.http.pipeline.authentication

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.method.HandlerMethod
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.utils.JwtProvider
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class AuthenticationInterceptorTests {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    private open class TestController

    private class UnauthenticatedTestController : TestController() {
        @Suppress("unused")
        fun test() {
        }
    }

    private class AuthenticatedTestController : TestController() {
        @Suppress("unused")
        @Authenticated
        fun test() {
        }
    }

    /**
     * Returns the HandlerMethod for testing.
     *
     * @param T the controller class (must derive from TestController in this testing environment)
     */
    private inline fun <reified T : TestController> handlerMethod() = HandlerMethod(
        /* bean = */ T::class,
        /* method = */ T::class.java.getMethod("test")
    )

    @Test
    fun `AuthenticationInterceptor creation is successful`() {
        AuthenticationInterceptor(jwtProvider)
    }

    @Test
    fun `preHandle returns true if the handler is not a HandlerMethod`() {
        val authenticationInterceptor = AuthenticationInterceptor(jwtProvider)
        val httpServletRequest = MockHttpServletRequest()
        val httpServletResponse = MockHttpServletResponse()

        val proceed = authenticationInterceptor.preHandle(
            httpServletRequest,
            httpServletResponse,
            1
        )

        assertTrue(proceed)
    }

    @Test
    fun `preHandle returns true if the handler is HandlerMethod without Authenticated annotation`() {
        val authenticationInterceptor = AuthenticationInterceptor(jwtProvider)
        val handlerMethod = handlerMethod<UnauthenticatedTestController>()
        val httpServletRequest = MockHttpServletRequest()
        val httpServletResponse = MockHttpServletResponse()

        val proceed = authenticationInterceptor.preHandle(
            request = httpServletRequest,
            response = httpServletResponse,
            handler = handlerMethod
        )

        assertTrue(proceed)
    }

    @Test
    fun `preHandle returns false and sends an error response if the handler has Authenticated annotation but is missing Authorization Header`() {
        val authenticationInterceptor = AuthenticationInterceptor(jwtProvider)
        val handlerMethod = handlerMethod<AuthenticatedTestController>()
        val httpServletRequest = MockHttpServletRequest()
        val httpServletResponse = MockHttpServletResponse()

        assertFailsWith<AuthenticationException> {
            authenticationInterceptor.preHandle(
                request = httpServletRequest,
                response = httpServletResponse,
                handler = handlerMethod
            )
        }
    }

    @Test
    fun `preHandle returns false and sends an error response if the handler has Authenticated annotation but the token is not a bearer token`() {
        val authenticationInterceptor = AuthenticationInterceptor(jwtProvider)
        val handlerMethod = handlerMethod<AuthenticatedTestController>()
        val httpServletRequest = MockHttpServletRequest().also {
            it.addHeader(
                /* name = */ "Authorization",
                /* value = */ "invalidToken"
            )
        }
        val httpServletResponse = MockHttpServletResponse()

        assertFailsWith<AuthenticationException> {
            authenticationInterceptor.preHandle(
                request = httpServletRequest,
                response = httpServletResponse,
                handler = handlerMethod
            )
        }
    }

    @Test
    fun `preHandle returns true and stores a token attribute if the handler has Authenticated annotation and the token is a bearer token`() {
        val authenticationInterceptor = AuthenticationInterceptor(jwtProvider)
        val handlerMethod = handlerMethod<AuthenticatedTestController>()
        val httpServletRequest = MockHttpServletRequest().also {
            it.addHeader(
                /* name = */ "Authorization",
                /* value = */ "Bearer bearerToken"
            )
        }
        val httpServletResponse = MockHttpServletResponse()

        val proceed = authenticationInterceptor.preHandle(
            request = httpServletRequest,
            response = httpServletResponse,
            handler = handlerMethod
        )

        assertEquals("bearerToken", httpServletRequest.getAttribute("token"))
        assertTrue(proceed)
    }
}
