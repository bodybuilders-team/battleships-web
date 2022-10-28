package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a ship deployment is invalid.
 *
 * @param msg exception message
 */
class InvalidShipDeploymentException(msg: String) : Exception(msg)
