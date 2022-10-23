package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a user tries to perform an action that requires him to be in a game, but he is not.
 *
 * @param msg the message of the exception
 */
class UserNotInGameException(msg: String) : Exception(msg)
