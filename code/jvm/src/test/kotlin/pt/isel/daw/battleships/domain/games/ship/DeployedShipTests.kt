package pt.isel.daw.battleships.domain.games.ship

import pt.isel.daw.battleships.domain.exceptions.InvalidDeployedShipException
import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.PlayerTests.Companion.defaultPlayer
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeployedShipTests {

    @Test
    fun `DeployedShip creation is successful`() {
        val player = defaultPlayer
        val type = defaultShipType
        val coordinate = defaultCoordinate

        val deployedShip = DeployedShip(
            player = player,
            type = type,
            coordinate = coordinate,
            orientation = Ship.Orientation.VERTICAL,
            lives = 5
        )

        val deployedShipId = DeployedShip::class.declaredMemberProperties
            .first { it.name == "id" }.also { it.isAccessible = true }
            .call(deployedShip) as Int?

        assertNull(deployedShipId)
        assertEquals(player, deployedShip.player)
        assertEquals(type, deployedShip.type)
        assertEquals(coordinate, deployedShip.coordinate)
        assertEquals(Ship.Orientation.VERTICAL, deployedShip.orientation)
        assertEquals(5, deployedShip.lives)
    }

    @Test
    fun `DeployedShip creation throws InvalidDeployedShipException if lives aren't between 0 and ship size`() {
        assertFailsWith<InvalidDeployedShipException> {
            DeployedShip(
                player = defaultPlayer,
                type = defaultShipType,
                coordinate = defaultCoordinate,
                orientation = Ship.Orientation.VERTICAL,
                lives = 8
            )
        }
    }

    @Test
    fun `isSunk returns true if lives is 0`() {
        val ship = DeployedShip(
            player = defaultPlayer,
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Ship.Orientation.VERTICAL,
            lives = 0
        )

        assertTrue(ship.isSunk)
    }

    @Test
    fun `isSunk returns false if lives isn't 0`() {
        val ship = DeployedShip(
            player = defaultPlayer,
            type = defaultShipType,
            coordinate = defaultCoordinate,
            orientation = Ship.Orientation.VERTICAL,
            lives = 5
        )

        assertFalse(ship.isSunk)
    }

    companion object {
        val defaultDeployedShip
            get() = DeployedShip(
                defaultPlayer,
                defaultShipType,
                defaultCoordinate,
                Ship.Orientation.VERTICAL,
                5
            )
    }
}
