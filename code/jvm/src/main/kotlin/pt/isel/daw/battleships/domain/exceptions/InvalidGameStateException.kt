package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a game state is invalid.
 *
 * @param msg exception message
 */
class InvalidGameStateException(msg: String) : Exception(msg)
