package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a ship type is invalid.
 *
 * @param msg exception message
 */
class InvalidShipTypeException(msg: String) : Exception(msg)
