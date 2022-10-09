package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a player already deployed a fleet.
 *
 * @param msg exception message
 */
class FleetAlreadyDeployedException(msg: String) : Exception(msg)
