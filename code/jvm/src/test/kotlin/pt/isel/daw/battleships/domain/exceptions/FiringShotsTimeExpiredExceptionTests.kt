package pt.isel.daw.battleships.domain.exceptions

import kotlin.test.Test
import kotlin.test.assertFailsWith

class FiringShotsTimeExpiredExceptionTests {

    @Test
    fun `FiringShotsTimeExpiredException creation is successful`() {
        FiringShotsTimeExpiredException("Test")
    }

    @Test
    fun `FiringShotsTimeExpiredException thrown successfully`() {
        assertFailsWith<FiringShotsTimeExpiredException> {
            throw FiringShotsTimeExpiredException("Test")
        }
    }
}