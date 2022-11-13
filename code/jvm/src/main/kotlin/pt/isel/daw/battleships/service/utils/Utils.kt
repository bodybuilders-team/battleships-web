package pt.isel.daw.battleships.service.utils

import java.util.Optional
import java.util.stream.Stream

/**
 * Returns the first element of this stream or null if the stream is empty.
 *
 * @receiver the stream
 * @param T the type of the stream elements
 *
 * @return the first element of this stream or null if the stream is empty
 */
fun <T> Stream<T>.findFirstOrNull(): T? = findFirst().getOrNull()

/**
 * Returns the value if it is present or null otherwise.
 *
 * @receiver the optional instance
 * @param T the type of the [Optional] value
 *
 * @return the value if it is present or null otherwise
 */
fun <T> Optional<T>.getOrNull(): T? = orElse(null)
