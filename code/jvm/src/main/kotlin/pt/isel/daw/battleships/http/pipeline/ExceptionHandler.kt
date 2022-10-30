package pt.isel.daw.battleships.http.pipeline

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.InvalidCoordinateException
import pt.isel.daw.battleships.domain.exceptions.InvalidGameConfigException
import pt.isel.daw.battleships.domain.exceptions.InvalidGameException
import pt.isel.daw.battleships.domain.exceptions.InvalidPlayerException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipDeploymentException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.domain.exceptions.InvalidShotException
import pt.isel.daw.battleships.domain.exceptions.UserNotInGameException
import pt.isel.daw.battleships.domain.exceptions.WaitingForPlayersTimeExpiredException
import pt.isel.daw.battleships.http.media.Problem
import pt.isel.daw.battleships.service.exceptions.AlreadyExistsException
import pt.isel.daw.battleships.service.exceptions.AlreadyJoinedException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import pt.isel.daw.battleships.service.exceptions.InvalidLoginException
import pt.isel.daw.battleships.service.exceptions.InvalidPaginationParamsException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidTurnException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.exceptions.RefreshTokenExpiredException
import java.net.URI
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
            RefreshTokenExpiredException::class,
            InvalidLoginException::class,
            FleetDeployTimeExpiredException::class,
            FiringShotsTimeExpiredException::class,
            WaitingForPlayersTimeExpiredException::class,
            InvalidCoordinateException::class,
            InvalidPlayerException::class,
            InvalidGameConfigException::class,
            InvalidGameException::class,
            InvalidShipTypeException::class,
            InvalidShotException::class,
            InvalidShipDeploymentException::class
        ]
    )
    fun handleBadRequest(
        request: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + ex.toProblemType()),
            title = ex.message ?: "Bad Request",
            status = HttpStatus.BAD_REQUEST.value()
        ).toResponse()

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
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + ex.toProblemType()),
            title = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Validation Error",
            status = HttpStatus.BAD_REQUEST.value()
        ).toResponse()

    /**
     * Handles [HttpMessageNotReadableException] exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleHttpMessageNotReadableExceptions(
        request: HttpServletRequest,
        ex: HttpMessageNotReadableException
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + "invalid-request-body"),
            title = "Invalid request body${
            ex.rootCause?.also { println(it) }.let {
                ": " +
                    when (it) {
                        is UnrecognizedPropertyException -> "Unknown property '${it.propertyName}'"
                        is JsonParseException -> it.originalMessage
                        is MissingKotlinParameterException -> "Missing property '${it.parameter.name}'"
                        else -> null
                    }
            }
            }",
            status = HttpStatus.BAD_REQUEST.value()
        ).toResponse()

    /**
     * Handles Unauthorized exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleUnauthorized(
        request: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + ex.toProblemType()),
            title = ex.message ?: "Unauthorized",
            status = HttpStatus.UNAUTHORIZED.value()
        ).toResponse()

    /**
     * Handles Forbidden exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [UserNotInGameException::class])
    fun handleForbidden(
        request: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + ex.toProblemType()),
            title = ex.message ?: "Forbidden",
            status = HttpStatus.FORBIDDEN.value()
        ).toResponse()

    /**
     * Handles all other uncaught exceptions.
     *
     * @param ex exception to handle
     * @param request the HTTP request
     * @return response entity with the error message
     */
    @ExceptionHandler(value = [Exception::class])
    fun handleUncaughtExceptions(
        request: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<Any> =
        Problem(
            type = URI.create(PROBLEMS_DOCS_URI + "internal-server-error"),
            title = "Internal Server Error",
            status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        ).toResponse()
            .also { ex.printStackTrace() }

    companion object {
        const val PROBLEMS_DOCS_URI =
            "https://github.com/isel-leic-daw/2022-daw-leic51d-g03/tree/main/docs/problems/"

        /**
         * Converts an exception to a problem type name, in kebab-case.
         *
         * @return the problem type name
         */
        fun Exception.toProblemType(): String =
            (this::class.simpleName ?: "Unknown")
                .replace("Exception", "")
                .replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}-${it.groupValues[2]}" }
                .lowercase()
    }
}
