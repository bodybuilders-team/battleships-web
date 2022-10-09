package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a ship is not valid.
 *
 * @param msg exception message
 */
class InvalidShotException(msg: String) : Exception(msg)
