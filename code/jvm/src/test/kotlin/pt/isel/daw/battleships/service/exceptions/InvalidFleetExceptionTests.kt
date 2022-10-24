package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidFleetExceptionTests {

    @Test
    fun `InvalidFleetException creation is successful`() {
        InvalidFleetException("Test")
    }

    @Test
    fun `InvalidFleetException thrown successfully`() {
        assertFailsWith<InvalidFleetException> {
            throw InvalidFleetException("Test")
        }
    }
}