package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a refresh token is invalid.
 *
 * @param msg exception message
 */
class InvalidRefreshTokenException(msg: String) : Exception(msg)
