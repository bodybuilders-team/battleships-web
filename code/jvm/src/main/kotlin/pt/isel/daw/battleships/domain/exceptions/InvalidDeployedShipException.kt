package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a deployed ship is invalid.
 *
 * @param msg exception message
 */
class InvalidDeployedShipException(msg: String) : Exception(msg)
