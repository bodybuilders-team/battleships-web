package pt.isel.daw.battleships.controllers.exceptionHandlers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.daw.battleships.services.exceptions.*

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(
        value = [
            AlreadyExistsException::class,
            NotFoundException::class,
            AlreadyJoinedException::class,
            InvalidPhaseException::class
        ]
    )
    fun handleBadRequest(
        ex: Exception
    ): ResponseEntity<Any> =
        handleException(ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [AuthenticationError::class])
    fun handleUnauthorized(
        ex: Exception
    ): ResponseEntity<Any> =
        handleException(
            ex,
            HttpStatus.UNAUTHORIZED
        )

    private fun handleException(ex: Exception, status: HttpStatus): ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"message\": \"${ex.message}\"}")
}
