package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a refresh token has expired.
 *
 * @param msg exception message
 */
class RefreshTokenExpiredException(msg: String) : Exception(msg)
