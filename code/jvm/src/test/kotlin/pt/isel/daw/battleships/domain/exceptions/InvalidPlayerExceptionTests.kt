package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidPlayerExceptionTests {

    @Test
    fun `InvalidPlayerException creation is successful`() {
        InvalidPlayerException("Test")
    }

    @Test
    fun `InvalidPlayerException thrown successfully`() {
        assertFailsWith<InvalidPlayerException> {
            throw InvalidPlayerException("Test")
        }
    }
}
