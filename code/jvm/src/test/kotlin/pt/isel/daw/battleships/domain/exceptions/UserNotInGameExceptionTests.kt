package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class UserNotInGameExceptionTests {

    @Test
    fun `UserNotInGameException creation is successful`() {
        UserNotInGameException("Test")
    }

    @Test
    fun `UserNotInGameException thrown successfully`() {
        assertFailsWith<UserNotInGameException> {
            throw UserNotInGameException("Test")
        }
    }
}
