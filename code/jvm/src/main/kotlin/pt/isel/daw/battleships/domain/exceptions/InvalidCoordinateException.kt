package pt.isel.daw.battleships.domain.exceptions

/**
 * Exception thrown when a coordinate is invalid.
 *
 * @param msg exception message
 */
class InvalidCoordinateException(msg: String) : Exception(msg)
