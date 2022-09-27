package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when the request is bad.
 *
 * @param msg the message to be returned to the client
 */
class BadRequestException(msg: String) : Exception(msg)
