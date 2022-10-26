package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidUserExceptionTests {

    @Test
    fun `InvalidUserException creation is successful`() {
        InvalidUserException("Test")
    }

    @Test
    fun `InvalidUserException thrown successfully`() {
        assertFailsWith<InvalidUserException> {
            throw InvalidUserException("Test")
        }
    }
}