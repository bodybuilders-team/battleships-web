package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when an invalid argument is passed to a service.
 *
 * @param msg exception message
 */
class InvalidArgumentException(msg: String) : Exception(msg)
