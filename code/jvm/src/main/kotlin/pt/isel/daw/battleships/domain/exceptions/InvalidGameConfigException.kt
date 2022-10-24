package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a game configuration is invalid.
 *
 * @param msg exception message
 */
class InvalidGameConfigException(msg: String) : Exception(msg)
