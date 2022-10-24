package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidTurnExceptionTests {

    @Test
    fun `InvalidTurnException creation is successful`() {
        InvalidTurnException("Test")
    }

    @Test
    fun `InvalidTurnException thrown successfully`() {
        assertFailsWith<InvalidTurnException> {
            throw InvalidTurnException("Test")
        }
    }
}