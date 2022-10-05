package pt.isel.daw.battleships.pipeline

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.daw.battleships.services.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.services.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.services.exceptions.AuthenticationException
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.services.exceptions.NotFoundException

// TODO: Check if this makes sense to be in the pipeline package
/**
 * Handles exceptions thrown by the controllers.
 */
@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Handles Bad Request exceptions.
     *
     * @param ex exception to handle
     * @return response entity with the error message
     */
    @ExceptionHandler(
        value = [
            AlreadyExistsException::class,
            NotFoundException::class,
            AlreadyJoinedException::class,
            InvalidPhaseException::class
        ]
    )
    fun handleBadRequest(ex: Exception): ResponseEntity<Any> =
        handleException(ex, HttpStatus.BAD_REQUEST)

    /**
     * Handles Unauthorized exceptions.
     *
     * @param ex exception to handle
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleUnauthorized(ex: Exception): ResponseEntity<Any> =
        handleException(
            ex,
            HttpStatus.UNAUTHORIZED
        )

    /**
     * Handles an exception.
     *
     * @param ex exception to handle
     * @param status status to return
     *
     * @return response entity with the error message
     */
    private fun handleException(ex: Exception, status: HttpStatus): ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"message\": \"${ex.message}\"}")
}
