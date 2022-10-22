package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a user tries to join a game that they are already in.
 *
 * @param msg exception message
 */
class AlreadyJoinedException(msg: String) : Exception(msg)
