package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a player tries to deploy a fleet after the deployment time has expired.
 *
 * @param msg exception message
 */
class FleetDeployTimeExpiredException(msg: String) : Exception(msg)
