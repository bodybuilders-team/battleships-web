package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidGameStateExceptionTests {

    @Test
    fun `InvalidGameStateException creation is successful`() {
        InvalidGameStateException("Test")
    }

    @Test
    fun `InvalidGameStateException thrown successfully`() {
        assertFailsWith<InvalidGameStateException> {
            throw InvalidGameStateException("Test")
        }
    }
}