package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when the player tries to make a move when it's not his turn.
 *
 * @param msg exception message
 */
class InvalidTurnException(msg: String) : Exception(msg)
