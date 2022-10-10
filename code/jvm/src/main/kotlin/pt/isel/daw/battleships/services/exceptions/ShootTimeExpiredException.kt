package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a player tries to shoot after the shoot time has expired.
 *
 * @param msg exception message
 */
class ShootTimeExpiredException(msg: String) : Exception(msg)
