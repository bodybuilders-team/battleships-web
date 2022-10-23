package pt.isel.daw.battleships.service.exceptions

/**
 * Exception thrown when a pagination parameter is invalid.
 *
 * @param msg exception message
 */
class InvalidPaginationParamsException(msg: String) : Exception(msg)
