package pt.isel.daw.battleships.services.exceptions

/**
 * Thrown when a resource already exists.
 *
 * @param msg exception message
 */
class AlreadyExistsException(msg: String) : Exception(msg)
