package pt.isel.daw.battleships.http.pipeline

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.validation.DataBinder
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import javax.validation.Valid
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
class ExceptionHandlerTests {

    @Autowired
    lateinit var handler: ExceptionHandler

    private val exceptionHandlerHandleErrorMessagePrivateKFunction by lazy {
        ExceptionHandler.Companion::class.declaredFunctions
            .first { it.name == "handleErrorMessage" }.also { it.isAccessible = true }
    }

    @Test
    fun `handleBadRequest returns ResponseEntity with BadRequest error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleBadRequest(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertEquals("[application/json]", responseEntity.headers["Content-Type"].toString())
        assertEquals("test", (responseEntity.body as Map<*, *>)["error"])
    }

    @Test
    fun `handleValidationExceptions returns ResponseEntity with BadRequest error`() {
        val httpServletRequest = MockHttpServletRequest()

        class TestController {
            @Suppress("unused")
            fun test(
                @Suppress("UNUSED_PARAMETER")
                @Valid
                test: String
            ) {
            }
        }

        val responseEntity = handler.handleValidationExceptions(
            request = httpServletRequest,
            ex = MethodArgumentNotValidException(
                MethodParameter(TestController::class.java.getMethod("test", String::class.java), 0),
                DataBinder("test").apply {
                    bindingResult.addError(FieldError("test", "test", "test"))
                }.bindingResult
            )
        )

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertEquals("[application/json]", responseEntity.headers["Content-Type"].toString())
        assertEquals("test", (responseEntity.body as Map<*, *>)["error"])
    }

    @Test
    fun `handleUnauthorized returns ResponseEntity with Unauthorized error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleUnauthorized(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
        assertEquals("[application/json]", responseEntity.headers["Content-Type"].toString())
        assertEquals("test", (responseEntity.body as Map<*, *>)["error"])
    }

    @Test
    fun `handleForbidden returns ResponseEntity with Forbidden error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleForbidden(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.statusCode)
        assertEquals("[application/json]", responseEntity.headers["Content-Type"].toString())
        assertEquals("test", (responseEntity.body as Map<*, *>)["error"])
    }

    @Test
    fun `handleErrorMessage returns ResponseEntity with the error message`() {
        val responseEntity = exceptionHandlerHandleErrorMessagePrivateKFunction.call(
            ExceptionHandler.Companion,
            "test",
            HttpStatus.I_AM_A_TEAPOT,
            "/test"
        ) as ResponseEntity<*>

        val body = responseEntity.body as Map<*, *>

        assertEquals(HttpStatus.I_AM_A_TEAPOT, responseEntity.statusCode)
        assertEquals(HttpStatus.I_AM_A_TEAPOT.value(), body["status"])
        assertEquals("test", body["error"])
        assertEquals("/test", body["path"])
    }
}
