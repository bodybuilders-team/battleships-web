package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a player tries to perform an action in a phase that is not allowed.
 *
 * @param msg exception message
 */
class InvalidPhaseException(msg: String) : Exception(msg)
