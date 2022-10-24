package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class WaitingForPlayersTimeExpiredExceptionTests {

    @Test
    fun `WaitingForPlayersTimeExpiredException creation is successful`() {
        WaitingForPlayersTimeExpiredException("Test")
    }

    @Test
    fun `WaitingForPlayersTimeExpiredException thrown successfully`() {
        assertFailsWith<WaitingForPlayersTimeExpiredException> {
            throw WaitingForPlayersTimeExpiredException("Test")
        }
    }
}
