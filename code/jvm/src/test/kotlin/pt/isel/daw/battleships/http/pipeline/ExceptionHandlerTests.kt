package pt.isel.daw.battleships.http.pipeline

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.validation.DataBinder
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import pt.isel.daw.battleships.http.media.Problem
import javax.validation.Valid
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
class ExceptionHandlerTests {

    @Autowired
    lateinit var handler: ExceptionHandler

    @Test
    fun `handleBadRequest returns ResponseEntity with BadRequest error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleBadRequest(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        assertEquals(
            Problem.PROBLEM_MEDIA_TYPE,
            responseEntity.headers["Content-Type"].toString().removeSurrounding("[", "]")
        )
        assertEquals("test", (responseEntity.body as Problem).title)
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
        assertEquals(
            Problem.PROBLEM_MEDIA_TYPE,
            responseEntity.headers["Content-Type"].toString().removeSurrounding("[", "]")
        )
        assertEquals("test", (responseEntity.body as Problem).title)
    }

    @Test
    fun `handleUnauthorized returns ResponseEntity with Unauthorized error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleUnauthorized(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
        assertEquals(
            Problem.PROBLEM_MEDIA_TYPE,
            responseEntity.headers["Content-Type"].toString().removeSurrounding("[", "]")
        )
        assertEquals("test", (responseEntity.body as Problem).title)
    }

    @Test
    fun `handleForbidden returns ResponseEntity with Forbidden error`() {
        val httpServletRequest = MockHttpServletRequest()

        val responseEntity = handler.handleForbidden(httpServletRequest, Exception("test"))

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.statusCode)
        assertEquals(
            Problem.PROBLEM_MEDIA_TYPE,
            responseEntity.headers["Content-Type"].toString().removeSurrounding("[", "]")
        )
        assertEquals("test", (responseEntity.body as Problem).title)
    }
}
