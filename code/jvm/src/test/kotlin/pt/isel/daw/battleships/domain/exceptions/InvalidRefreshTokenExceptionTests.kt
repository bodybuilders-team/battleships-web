package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidRefreshTokenExceptionTests {

    @Test
    fun `InvalidRefreshTokenException creation is successful`() {
        InvalidRefreshTokenException("Test")
    }

    @Test
    fun `InvalidRefreshTokenException thrown successfully`() {
        assertFailsWith<InvalidRefreshTokenException> {
            throw InvalidRefreshTokenException("Test")
        }
    }
}