package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a pagination parameter is invalid.
 *
 * @param msg exception message
 */
class InvalidPaginationParams(msg: String) : Exception(msg)
