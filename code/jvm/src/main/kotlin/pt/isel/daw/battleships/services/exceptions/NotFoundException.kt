package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a resource is not found.
 *
 * @param s the message to be returned to the client
 */
class NotFoundException(s: String) : Exception(s)
