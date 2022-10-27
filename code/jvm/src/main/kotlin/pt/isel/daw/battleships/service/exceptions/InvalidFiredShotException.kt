package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a shot is not valid.
 *
 * @param msg exception message
 */
class InvalidFiredShotException(msg: String) : Exception(msg)
