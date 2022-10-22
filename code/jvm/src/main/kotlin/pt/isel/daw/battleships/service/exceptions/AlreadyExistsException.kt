package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a resource already exists.
 *
 * @param msg exception message
 */
class AlreadyExistsException(msg: String) : Exception(msg)
