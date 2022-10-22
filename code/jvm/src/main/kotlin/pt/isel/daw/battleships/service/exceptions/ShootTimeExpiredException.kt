package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a player tries to shoot after the shoot time has expired.
 *
 * @param msg exception message
 */
class ShootTimeExpiredException(msg: String) : Exception(msg)
