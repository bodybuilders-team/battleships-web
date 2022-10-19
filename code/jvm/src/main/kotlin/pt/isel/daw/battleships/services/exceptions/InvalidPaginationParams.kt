package pt.isel.daw.battleships.services.exceptions

/**
 * Exception thrown when a pagination parameter is invalid.
 *
 * @param msg exception message
 */
class InvalidPaginationParams(msg: String) : Exception(msg)
