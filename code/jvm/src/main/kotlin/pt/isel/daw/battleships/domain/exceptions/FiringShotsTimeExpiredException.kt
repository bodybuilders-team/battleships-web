package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a player tries to fire a shot after the firing shots time has expired.
 *
 * @param msg exception message
 */
class FiringShotsTimeExpiredException(msg: String) : Exception(msg)
