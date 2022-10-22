package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a player already deployed a fleet.
 *
 * @param msg exception message
 */
class FleetAlreadyDeployedException(msg: String) : Exception(msg)
