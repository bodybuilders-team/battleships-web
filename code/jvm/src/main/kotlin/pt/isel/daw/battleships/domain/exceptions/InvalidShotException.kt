package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a shot is invalid.
 *
 * @param msg exception message
 */
class InvalidShotException(msg: String) : Exception(msg)
