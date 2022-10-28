package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidShipTypeExceptionTests {

    @Test
    fun `InvalidShipTypeException creation is successful`() {
        InvalidShipTypeException("Test")
    }

    @Test
    fun `InvalidShipTypeException thrown successfully`() {
        assertFailsWith<InvalidShipTypeException> {
            throw InvalidShipTypeException("Test")
        }
    }
}
