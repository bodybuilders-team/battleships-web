package pt.isel.daw.battleships.service.games.dtos.ship

import pt.isel.daw.battleships.domain.Coordinate
import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameConfig
import pt.isel.daw.battleships.domain.game.GameState
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.Ship
import pt.isel.daw.battleships.domain.ship.ShipType
import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class OutputDeployedShipDTOTests {

    @Test
    fun `OutputShipDTO creation is successful`() {
        DeployedShipDTO(
            type = "type",
            coordinate = CoordinateDTO('A', 1),
            orientation = "HORIZONTAL",
            lives = 3
        )
    }

    @Test
    fun `OutputShipDTO from Ship conversion is successful`() {
        val user = User(username = "username", email = "email", passwordHash = "password", points = 0)
        val player = Player(
            game = Game(
                name = "game",
                creator = user,
                config = GameConfig(
                    gridSize = 1,
                    maxTimePerRound = 2,
                    maxTimeForLayoutPhase = 4,
                    shotsPerRound = 3,
                    shipTypes = listOf()
                ),
                state = GameState(
                    phase = GameState.GamePhase.WAITING_FOR_PLAYERS,
                    phaseEndTime = Timestamp(Instant.now().epochSecond),
                    round = 1,
                    turn = null,
                    winner = null
                )
            ),
            user = user,
            points = 0
        )

        val deployedShip = DeployedShip(
            player = player,
            type = ShipType(shipName = "type", size = 3, quantity = 1, points = 10),
            coordinate = Coordinate(col = 'A', row = 1),
            orientation = Ship.Orientation.HORIZONTAL,
            lives = 3
        )
        val deployedShipDTO = DeployedShipDTO(deployedShip)

        assertEquals(deployedShip.type.shipName, deployedShipDTO.type)
        assertEquals(CoordinateDTO(deployedShip.coordinate), deployedShipDTO.coordinate)
        assertEquals("HORIZONTAL", deployedShipDTO.orientation)
        assertEquals(deployedShip.lives, deployedShipDTO.lives)
    }
}
