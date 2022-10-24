package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a game is invalid.
 *
 * @param msg exception message
 */
class InvalidGameException(msg: String) : Exception(msg)
