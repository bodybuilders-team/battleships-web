package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a resource is not found.
 *
 * @param msg the message to be returned to the client
 */
class NotFoundException(msg: String) : Exception(msg)
