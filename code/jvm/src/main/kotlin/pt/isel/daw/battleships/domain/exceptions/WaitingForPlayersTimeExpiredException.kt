package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when the waiting time for players to join a game has expired.
 *
 * @property msg the message of the exception
 */
class WaitingForPlayersTimeExpiredException(msg: String) : Exception(msg)
