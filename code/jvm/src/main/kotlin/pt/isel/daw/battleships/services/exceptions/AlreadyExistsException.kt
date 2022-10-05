package pt.isel.daw.battleships.services.exceptions

/**
 * Thrown when a resource already exists.
 *
 * @param s exception message
 */
class AlreadyExistsException(s: String) : Exception(s)
