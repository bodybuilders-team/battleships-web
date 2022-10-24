package pt.isel.daw.battleships.service.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class AuthenticationExceptionTests {

    @Test
    fun `AuthenticationException creation is successful`() {
        AuthenticationException("Test")
    }

    @Test
    fun `AuthenticationException thrown successfully`() {
        assertFailsWith<AuthenticationException> {
            throw AuthenticationException("Test")
        }
    }
}
