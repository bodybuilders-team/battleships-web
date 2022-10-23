package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when the waiting time for players to join a game has expired.
 *
 * @param msg exception message
 */
class WaitingForPlayersTimeExpiredException(msg: String) : Exception(msg)
