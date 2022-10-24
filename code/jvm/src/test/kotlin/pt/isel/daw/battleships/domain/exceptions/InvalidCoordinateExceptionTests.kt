package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidCoordinateExceptionTests {

    @Test
    fun `InvalidCoordinateException creation is successful`() {
        InvalidCoordinateException("Test")
    }

    @Test
    fun `InvalidCoordinateException thrown successfully`() {
        assertFailsWith<InvalidCoordinateException> {
            throw InvalidCoordinateException("Test")
        }
    }
}