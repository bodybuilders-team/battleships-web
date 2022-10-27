package pt.isel.daw.battleships.http.pipeline

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.InvalidCoordinateException
import pt.isel.daw.battleships.domain.exceptions.InvalidDeployedShipException
import pt.isel.daw.battleships.domain.exceptions.InvalidGameConfigException
import pt.isel.daw.battleships.domain.exceptions.InvalidGameException
import pt.isel.daw.battleships.domain.exceptions.InvalidPlayerException
import pt.isel.daw.battleships.domain.exceptions.InvalidRefreshTokenException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.domain.exceptions.InvalidShotException
import pt.isel.daw.battleships.domain.exceptions.InvalidUserException
import pt.isel.daw.battleships.domain.exceptions.UserNotInGameException
import pt.isel.daw.battleships.domain.exceptions.WaitingForPlayersTimeExpiredException
import pt.isel.daw.battleships.service.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.service.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidTurnException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * Handles exceptions thrown by the controllers.
 */
@ControllerAdvice
class ExceptionHandler {

    /**
     * Handles Bad Request exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
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
            InvalidTurnException::class,
            NotFoundException::class,
            FleetDeployTimeExpiredException::class,
            FiringShotsTimeExpiredException::class,
            WaitingForPlayersTimeExpiredException::class,
            InvalidCoordinateException::class,
            InvalidUserException::class,
            InvalidPlayerException::class,
            InvalidRefreshTokenException::class,
            InvalidDeployedShipException::class,
            InvalidGameConfigException::class,
            InvalidGameException::class,
            InvalidShipTypeException::class,
            InvalidShotException::class
        ]
    )
    fun handleBadRequest(request: HttpServletRequest, ex: Exception): ResponseEntity<Any> =
        handleErrorMessage(
            message = ex.message ?: "Bad Request",
            status = HttpStatus.BAD_REQUEST,
            path = request.requestURI
        )

    /**
     * Handles validation exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidationExceptions(
        request: HttpServletRequest,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Any> {
        val message = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Validation Error"

        return handleErrorMessage(
            message = message,
            status = HttpStatus.BAD_REQUEST,
            path = request.requestURI
        )
    }

    /**
     * Handles Unauthorized exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleUnauthorized(request: HttpServletRequest, ex: Exception): ResponseEntity<Any> =
        handleErrorMessage(
            message = ex.message ?: "Unauthorized",
            status = HttpStatus.UNAUTHORIZED,
            path = request.requestURI
        )

    /**
     * Handles Forbidden exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [UserNotInGameException::class])
    fun handleForbidden(request: HttpServletRequest, ex: Exception): ResponseEntity<Any> =
        handleErrorMessage(
            message = ex.message ?: "Forbidden",
            status = HttpStatus.FORBIDDEN,
            path = request.requestURI
        )

    companion object {
        private const val ERROR_MESSAGE_KEY = "error"
        private const val TIMESTAMP_KEY = "timestamp"
        private const val PATH_KEY = "path"
        private const val STATUS_CODE_KEY = "status"

        /**
         * Handles an exception.
         *
         * @param message the error message
         * @param status status to return
         * @param path path of the request
         *
         * @return response entity with the error message
         */
        private fun handleErrorMessage(message: String, status: HttpStatus, path: String): ResponseEntity<Any> {
            val body = mutableMapOf<String, Any>()
            body[TIMESTAMP_KEY] = LocalDateTime.now()
            body[STATUS_CODE_KEY] = status.value()
            body[ERROR_MESSAGE_KEY] = message
            body[PATH_KEY] = path

            return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
        }
    }
}
