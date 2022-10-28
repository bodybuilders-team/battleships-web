package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.exceptions.InvalidShotException
import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.PlayerTests.Companion.defaultPlayer
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class ShotTests {

    @Test
    fun `Shot creation is successful`() {
        val player = defaultPlayer
        val coordinate = defaultCoordinate

        val shot = Shot(
            round = 1,
            player = player,
            coordinate = coordinate,
            result = Shot.ShotResult.HIT
        )

        val shotId = Shot::class.declaredMemberProperties
            .first { it.name == "id" }.also { it.isAccessible = true }
            .call(shot) as Int?

        assertNull(shotId)
        assertEquals(1, shot.round)
        assertEquals(player, shot.player)
        assertEquals(coordinate, shot.coordinate)
        assertEquals(Shot.ShotResult.HIT, shot.result)
    }

    @Test
    fun `Shot creation throws InvalidShotException with invalid round`() {
        assertFailsWith<InvalidShotException> {
            Shot(
                round = 0,
                player = defaultPlayer,
                coordinate = defaultCoordinate,
                result = Shot.ShotResult.HIT
            )
        }
    }

    companion object {
        val defaultShot
            get() = Shot(
                round = 1,
                player = defaultPlayer,
                coordinate = defaultCoordinate,
                result = Shot.ShotResult.HIT
            )
    }
}
