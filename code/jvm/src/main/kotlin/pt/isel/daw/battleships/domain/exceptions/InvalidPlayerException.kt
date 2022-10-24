package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a player is invalid.
 *
 * @param msg exception message
 */
class InvalidPlayerException(msg: String) : Exception(msg)
