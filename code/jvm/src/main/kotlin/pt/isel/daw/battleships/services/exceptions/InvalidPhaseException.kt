package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a user tries to perform an action in a phase that is not allowed.
 *
 * @param msg exception message
 */
class InvalidPhaseException(msg: String) : Exception(msg)
