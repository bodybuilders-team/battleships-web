package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidPhaseExceptionTests {

    @Test
    fun `InvalidPhaseException creation is successful`() {
        InvalidPhaseException("Test")
    }

    @Test
    fun `InvalidPhaseException thrown successfully`() {
        assertFailsWith<InvalidPhaseException> {
            throw InvalidPhaseException("Test")
        }
    }
}
