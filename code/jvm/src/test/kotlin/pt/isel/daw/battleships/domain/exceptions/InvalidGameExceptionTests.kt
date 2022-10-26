package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidGameExceptionTests {

    @Test
    fun `InvalidGameException creation is successful`() {
        InvalidGameException("Test")
    }

    @Test
    fun `InvalidGameException thrown successfully`() {
        assertFailsWith<InvalidGameException> {
            throw InvalidGameException("Test")
        }
    }
}