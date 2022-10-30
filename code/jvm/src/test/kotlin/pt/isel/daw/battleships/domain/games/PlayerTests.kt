package pt.isel.daw.battleships.domain.games

import pt.isel.daw.battleships.domain.exceptions.InvalidPlayerException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipDeploymentException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.domain.games.CoordinateTests.Companion.defaultCoordinate
import pt.isel.daw.battleships.domain.games.ShotTests.Companion.defaultShot
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.domain.games.game.GameStateTests
import pt.isel.daw.battleships.domain.games.game.GameTests.Companion.defaultGame
import pt.isel.daw.battleships.domain.games.ship.DeployedShip
import pt.isel.daw.battleships.domain.games.ship.DeployedShipTests.Companion.defaultDeployedShip
import pt.isel.daw.battleships.domain.games.ship.Ship
import pt.isel.daw.battleships.domain.games.ship.ShipType
import pt.isel.daw.battleships.domain.games.ship.ShipTypeTests.Companion.defaultShipType
import pt.isel.daw.battleships.domain.games.ship.UndeployedShip
import pt.isel.daw.battleships.domain.games.ship.UndeployedShipTests.Companion.defaultUndeployedShip
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFiredShotException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PlayerTests {

    private val playerIsValidShipCoordinatePrivateKFunction by lazy {
        Player::class.declaredFunctions
            .first { it.name == "isValidShipCoordinate" }.also { it.isAccessible = true }
    }

    /**
     * Private isValidShipCoordinate function of [Player] obtained by reflection.
     *
     * @receiver the player
     * @param coordinate the coordinate
     * @param orientation the orientation of the ship
     * @param size the size of the ship
     */
    private fun Player.isValidShipCoordinate(
        coordinate: Coordinate,
        orientation: Ship.Orientation,
        size: Int
    ) = playerIsValidShipCoordinatePrivateKFunction.call(this, coordinate, orientation, size) as Boolean

    @Test
    fun `Player creation is successful`() {
        val game = defaultGame
        val user = defaultUser(0)

        val player = Player(
            game = game,
            user = user,
            points = 0
        )

        val playerId = Player::class.declaredMemberProperties
            .first { it.name == "id" }.also { it.isAccessible = true }
            .call(player) as Int?

        assertNull(playerId)
        assertEquals(game, player.game)
        assertEquals(user, player.user)
        assertEquals(0, player.points)
        assertEquals(0, player.deployedShips.size)
        assertEquals(0, player.shots.size)
    }

    @Test
    fun `Player creation throws InvalidPlayerException if points is negative`() {
        assertFailsWith<InvalidPlayerException> {
            Player(
                game = defaultGame,
                user = defaultUser(0),
                points = -1
            )
        }
    }

    @Test
    fun `deployFleet deploys a fleet of ships`() {
        val undeployedShip2 = UndeployedShip(
            type = ShipType(
                shipName = "Destroyer",
                size = 2,
                quantity = 1,
                points = 2
            ),
            coordinate = Coordinate('D', 5),
            orientation = Ship.Orientation.HORIZONTAL
        )

        val player = Player(
            game = Game(
                name = "name",
                creator = defaultUser(0),
                config = GameConfig(
                    gridSize = 10,
                    maxTimePerRound = 60,
                    maxTimeForLayoutPhase = 60,
                    shotsPerRound = 4,
                    shipTypes = listOf(defaultShipType, undeployedShip2.type)
                ),
                state = GameStateTests.defaultGameState
            ),
            user = defaultUser(0),
            points = 0
        )

        assertEquals(0, player.deployedShips.size)

        player.deployFleet(listOf(defaultUndeployedShip, undeployedShip2))

        assertEquals(2, player.deployedShips.size)

        assertEquals(player, player.deployedShips[0].player)
        assertEquals(defaultUndeployedShip.type, player.deployedShips[0].type)
        assertEquals(defaultUndeployedShip.coordinate, player.deployedShips[0].coordinate)
        assertEquals(defaultUndeployedShip.orientation, player.deployedShips[0].orientation)
        assertEquals(defaultUndeployedShip.type.size, player.deployedShips[0].lives)

        assertEquals(player, player.deployedShips[1].player)
        assertEquals(undeployedShip2.type, player.deployedShips[1].type)
        assertEquals(undeployedShip2.coordinate, player.deployedShips[1].coordinate)
        assertEquals(undeployedShip2.orientation, player.deployedShips[1].orientation)
        assertEquals(undeployedShip2.type.size, player.deployedShips[1].lives)
    }

    @Test
    fun `deployFleet throws FleetAlreadyDeployedException if the player already has ships deployed`() {
        val player = defaultPlayer

        player.deployedShips.add(defaultDeployedShip)

        assertFailsWith<FleetAlreadyDeployedException> {
            player.deployFleet(listOf(defaultUndeployedShip))
        }
    }

    @Test
    fun `deployFleet throws InvalidFleetException if the fleet is invalid for the game configuration`() {
        val player = defaultPlayer

        assertFailsWith<InvalidFleetException> {
            player.deployFleet(
                listOf(
                    UndeployedShip(
                        type = ShipType(
                            shipName = "Destroyer",
                            size = 2,
                            quantity = 1,
                            points = 2
                        ),
                        coordinate = Coordinate('D', 5),
                        orientation = Ship.Orientation.HORIZONTAL
                    )
                )
            )
        }
    }

    @Test
    fun `deployFleet throws InvalidShipTypeException if a ship is out of bounds for the game grid`() {
        val player = defaultPlayer

        assertFailsWith<InvalidShipTypeException> {
            player.deployFleet(
                listOf(
                    UndeployedShip(
                        type = defaultShipType,
                        coordinate = Coordinate('K', 5),
                        orientation = Ship.Orientation.HORIZONTAL
                    )
                )
            )
        }
    }

    @Test
    fun `deployFleet throws InvalidShipDeploymentException if there are overlapping ships`() {
        val undeployedShip2 = UndeployedShip(
            type = ShipType(
                shipName = "Destroyer",
                size = 2,
                quantity = 1,
                points = 2
            ),
            coordinate = Coordinate('A', 3),
            orientation = Ship.Orientation.HORIZONTAL
        )

        val player = Player(
            game = Game(
                name = "name",
                creator = defaultUser(0),
                config = GameConfig(
                    gridSize = 10,
                    maxTimePerRound = 60,
                    maxTimeForLayoutPhase = 60,
                    shotsPerRound = 4,
                    shipTypes = listOf(defaultShipType, undeployedShip2.type)
                ),
                state = GameStateTests.defaultGameState
            ),
            user = defaultUser(0),
            points = 0
        )

        assertEquals(0, player.deployedShips.size)

        assertFailsWith<InvalidShipDeploymentException> {
            player.deployFleet(listOf(defaultUndeployedShip, undeployedShip2))
        }
    }

    @Test
    fun `isValidShipCoordinate returns true if the coordinate is valid`() {
        val player = defaultPlayer

        assertTrue(
            player.isValidShipCoordinate(
                coordinate = Coordinate('A', 1),
                orientation = Ship.Orientation.HORIZONTAL,
                size = 1
            )
        )
    }

    @Test
    fun `isValidShipCoordinate returns false if the coordinate is invalid`() {
        val player = defaultPlayer

        assertFalse(
            player.isValidShipCoordinate(
                coordinate = Coordinate('K', 1),
                orientation = Ship.Orientation.HORIZONTAL,
                size = 1
            )
        )
    }

    @Test
    fun `shoot returns the shots made`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1

        val shots = player.shoot(listOf(Coordinate('A', 1), Coordinate('A', 2)))

        assertEquals(2, shots.size)

        assertEquals(player, shots[0].player)
        assertEquals(Coordinate('A', 1), shots[0].coordinate)
        assertEquals(Shot.ShotResult.MISS, shots[0].result)
        assertEquals(1, shots[0].round)

        assertEquals(player, shots[1].player)
        assertEquals(Coordinate('A', 2), shots[1].coordinate)
        assertEquals(Shot.ShotResult.MISS, shots[1].result)
        assertEquals(1, shots[1].round)
    }

    @Test
    fun `shoot adds shots to the player if player had no shots before`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1

        assertEquals(0, player.shots.size)

        val shots = player.shoot(listOf(Coordinate('A', 1)))

        assertEquals(shots.size, player.shots.size)
        assertEquals(shots, player.shots)
    }

    @Test
    fun `shoot adds shots to the player if player had shots before`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        val shot = defaultShot
        player.shots.add(shot)

        val shots = player.shoot(listOf(Coordinate('A', 6)))

        assertEquals(1 + shots.size, player.shots.size)
        assertEquals(listOf(shot) + shots, player.shots)
    }

    @Test
    fun `shoot sets made shot result to HIT if it hit a ship`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        player2.deployedShips.add(defaultDeployedShip)

        val shots = player.shoot(listOf(defaultCoordinate))

        assertEquals(Shot.ShotResult.HIT, shots[0].result)
    }

    @Test
    fun `shoot sets made shot result to MISS if it didn't hit any ship (missed)`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1

        val shots = player.shoot(listOf(Coordinate('A', 1)))

        assertEquals(Shot.ShotResult.MISS, shots[0].result)
    }

    @Test
    fun `shoot sets made shot result to SUNK if it sank a ship`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        player2.deployedShips.add(
            DeployedShip(
                player = player2,
                type = ShipType(
                    shipName = "MiniTestShip",
                    quantity = 1,
                    size = 1,
                    points = 1
                ),
                coordinate = defaultCoordinate,
                orientation = Ship.Orientation.HORIZONTAL,
                lives = 1
            )
        )

        val shots = player.shoot(listOf(defaultCoordinate))

        assertEquals(Shot.ShotResult.SUNK, shots[0].result)
    }

    @Test
    fun `shoot decrements a ship's lives by one if it is hit by a shot`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        val deployedShip = defaultDeployedShip
        player2.deployedShips.add(deployedShip)

        val deployedShipLives = deployedShip.lives

        player.shoot(listOf(Coordinate('A', 1)))

        assertEquals(deployedShipLives - 1, player2.deployedShips[0].lives)
    }

    @Test
    fun `shoot does not affect a ship's lives if it is missed by a shot`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        val deployedShip = defaultDeployedShip
        player2.deployedShips.add(deployedShip)

        val deployedShipLives = deployedShip.lives

        player.shoot(listOf(Coordinate('D', 3)))

        assertEquals(deployedShipLives, player2.deployedShips[0].lives)
    }

    @Test
    fun `shoot adds ship points to player points if a shot sank a ship`() {
        val game = defaultGame
        val user = defaultUser(0)
        val user2 = User(
            username = "Player 2",
            email = "haram2@email.com",
            passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
            points = 0
        )
        val player = Player(game = game, user = user)
        val player2 = Player(game = game, user = user2)
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        game.state.round = 1
        player2.deployedShips.add(
            DeployedShip(
                player = player2,
                type = ShipType(
                    shipName = "MiniTestShip",
                    quantity = 1,
                    size = 1,
                    points = 15
                ),
                coordinate = defaultCoordinate,
                orientation = Ship.Orientation.HORIZONTAL,
                lives = 1
            )
        )

        val playerPoints = player.points

        player.shoot(listOf(defaultCoordinate))

        assertEquals(playerPoints + 15, player.points)
    }

    @Test
    fun `shoot throws InvalidFiredShotException if shot is out of bounds for the game grid`() {
        val player = defaultPlayer

        assertFailsWith<InvalidFiredShotException> {
            player.shoot(listOf(Coordinate('K', 6)))
        }
    }

    @Test
    fun `shoot throws InvalidFiredShotException if at least two shots have the same coordinates`() {
        val player = defaultPlayer

        assertFailsWith<InvalidFiredShotException> {
            player.shoot(listOf(Coordinate('A', 1), Coordinate('A', 1)))
        }
    }

    @Test
    fun `shoot throws InvalidFiredShotException if there's a shot to an already shot coordinate`() {
        val player = defaultPlayer

        player.shots.add(defaultShot)

        assertFailsWith<InvalidFiredShotException> {
            player.shoot(listOf(defaultCoordinate))
        }
    }

    companion object {
        val defaultPlayer
            get() = Player(
                game = defaultGame,
                user = defaultUser(0),
                points = 0
            )
    }
}
