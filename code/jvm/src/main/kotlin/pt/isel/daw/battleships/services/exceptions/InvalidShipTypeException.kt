package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a ship type is invalid.
 *
 * @param msg exception message
 */
class InvalidShipTypeException(msg: String) : Exception(msg)
