package pt.isel.daw.battleships.service.utils

import java.util.Optional
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StreamUtilsTests {

    @Test
    fun `findFirstOrNull returns the first element of the stream if it is not empty`() {
        val stream = Stream.of(1, 2, 3)

        val first = stream.findFirstOrNull()

        assertNotNull(first)
        assertEquals(1, first)
    }

    @Test
    fun `findFirstOrNull returns null if the stream is empty`() {
        val stream = Stream.empty<Int>()

        val first = stream.findFirstOrNull()

        assertNull(first)
    }

    @Test
    fun `getOrNull returns the value if it is present`() {
        val optional = Optional.of(1)

        val value = optional.getOrNull()

        assertNotNull(value)
        assertEquals(1, value)
    }

    @Test
    fun `getOrNull returns null if the value is not present`() {
        val optional = Optional.empty<Int>()

        val value = optional.getOrNull()

        assertNull(value)
    }
}
