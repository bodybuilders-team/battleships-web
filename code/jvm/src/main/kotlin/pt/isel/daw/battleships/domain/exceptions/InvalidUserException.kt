package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a user is invalid.
 *
 * @param msg exception message
 */
class InvalidUserException(msg: String) : Exception(msg)
