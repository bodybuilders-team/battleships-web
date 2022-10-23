package pt.isel.daw.battleships.http.pipeline

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.InvalidCoordinate
import pt.isel.daw.battleships.domain.exceptions.WaitingForPlayersTimeExpiredException
import pt.isel.daw.battleships.service.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.service.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.service.exceptions.InvalidShotException
import pt.isel.daw.battleships.service.exceptions.InvalidTurnException
import pt.isel.daw.battleships.service.exceptions.NotFoundException

/**
 * Handles exceptions thrown by the controllers.
 */
@ControllerAdvice
class ExceptionHandler {

    /**
     * Handles Bad Request exceptions.
     *
     * @param ex exception to handle
     * @return response entity with the error message
     */
    @ExceptionHandler(
        value = [
            AlreadyExistsException::class,
            AlreadyJoinedException::class,
            FleetAlreadyDeployedException::class,
            InvalidFleetException::class,
            InvalidPaginationParamsException::class,
            InvalidPhaseException::class,
            InvalidShipTypeException::class,
            InvalidShotException::class,
            InvalidTurnException::class,
            NotFoundException::class,
            FleetDeployTimeExpiredException::class,
            FiringShotsTimeExpiredException::class,
            WaitingForPlayersTimeExpiredException::class,
            InvalidCoordinate::class
        ]
    )
    fun handleBadRequest(ex: Exception): ResponseEntity<Any> =
        handleException(
            ex = ex,
            status = HttpStatus.BAD_REQUEST
        )

    /**
     * Handles Unauthorized exceptions.
     *
     * @param ex exception to handle
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleUnauthorized(ex: Exception): ResponseEntity<Any> =
        handleException(
            ex = ex,
            status = HttpStatus.UNAUTHORIZED
        )

    companion object {
        private const val ERROR_MESSAGE_KEY = "message"

        /**
         * Handles an exception.
         *
         * @param ex exception to handle
         * @param status status to return
         *
         * @return response entity with the error message
         */
        private fun handleException(ex: Exception, status: HttpStatus): ResponseEntity<Any> {
            val body = mutableMapOf<String, Any>()
            body[ERROR_MESSAGE_KEY] = ex.message ?: "An error occurred"

            return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
        }
    }
}
