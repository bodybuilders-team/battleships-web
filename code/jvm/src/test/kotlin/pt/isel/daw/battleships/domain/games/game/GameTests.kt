package pt.isel.daw.battleships.domain.games.game

import pt.isel.daw.battleships.domain.exceptions.InvalidGameException
import pt.isel.daw.battleships.domain.exceptions.UserNotInGameException
import pt.isel.daw.battleships.domain.games.Player
import pt.isel.daw.battleships.domain.games.game.GameConfigTests.Companion.defaultGameConfig
import pt.isel.daw.battleships.domain.games.game.GameStateTests.Companion.defaultGameState
import pt.isel.daw.battleships.domain.games.ship.UndeployedShipTests.Companion.defaultUndeployedShip
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.domain.users.UserTests.Companion.defaultUser
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameTests {

    @Test
    fun `Game creation is successful`() {
        Game(
            name = "name",
            creator = defaultUser,
            config = defaultGameConfig,
            state = defaultGameState
        )
    }

    @Test
    fun `Game creation throws InvalidGameException if name is shorter than min length`() {
        assertFailsWith<InvalidGameException> {
            Game(
                "",
                defaultUser,
                defaultGameConfig,
                defaultGameState
            )
        }
    }

    @Test
    fun `Game creation throws InvalidGameException if name is longer than max length`() {
        assertFailsWith<InvalidGameException> {
            Game(
                "n".repeat(41),
                defaultUser,
                defaultGameConfig,
                defaultGameState
            )
        }
    }

    @Test
    fun `getPlayer returns the player with the given username if they exist in game`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        game.addPlayer(player = player)

        val foundPlayer = game.getPlayer(defaultUser.username)

        assertEquals(player, foundPlayer)
    }

    @Test
    fun `getPlayer throws UserNotInGameException if the player with the given username does not exist in game`() {
        val game = defaultGame

        assertFailsWith<UserNotInGameException> {
            game.getPlayer(defaultUser.username)
        }
    }

    @Test
    fun `hasPlayer returns true if the player with the given username exists in game`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        game.addPlayer(player = player)

        val playerExists = game.hasPlayer(defaultUser.username)

        assertTrue(playerExists)
    }

    @Test
    fun `hasPlayer returns false if the player with the given username does not exist in game`() {
        val game = defaultGame

        val playerExists = game.hasPlayer(defaultUser.username)

        assertFalse(playerExists)
    }

    @Test
    fun `getPlayerOrNull returns the player with the given username if they exist in game`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        game.addPlayer(player = player)

        val foundPlayer = game.getPlayerOrNull(defaultUser.username)

        assertNotNull(foundPlayer)
        assertEquals(player, foundPlayer)
    }

    @Test
    fun `getPlayerOrNull returns null if the player with the given username does not exist in game`() {
        val game = defaultGame

        val foundPlayer = game.getPlayerOrNull(defaultUser.username)

        assertNull(foundPlayer)
    }

    @Test
    fun `getOpponent returns the opponent of the player with the given username  if there's an opponent`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        game.addPlayer(player = player)
        val opponent = Player(
            game = game,
            user = User(
                username = "Player 2",
                email = "haram2@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )
        game.addPlayer(player = opponent)

        val foundOpponent = game.getOpponent(player)

        assertEquals(opponent, foundOpponent)
    }

    @Test
    fun `getOpponent throws NotFoundException if there's no opponent`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        game.addPlayer(player = player)

        assertFailsWith<NotFoundException> {
            game.getOpponent(player)
        }
    }

    @Test
    fun `addPlayer adds a player to the game`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)

        game.addPlayer(player = player)

        assertTrue(game.hasPlayer(defaultUser.username))
    }

    @Test
    fun `addPlayer throws IllegalStateException if the game already has 2 players`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        val player2 = Player(
            game = game,
            user = User(
                username = "Player 2",
                email = "haram2@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        val player3 = Player(
            game = game,
            user = User(
                username = "Player 3",
                email = "haram3@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )

        assertFailsWith<IllegalStateException> {
            game.addPlayer(player = player3)
        }
    }

    @Test
    fun `areFleetsDeployed returns true if both players deployed their fleets`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        val player2 = Player(
            game = game,
            user = User(
                username = "Player 2",
                email = "haram2@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )
        game.addPlayer(player = player)
        game.addPlayer(player = player2)
        player.deployFleet(listOf(defaultUndeployedShip))
        player2.deployFleet(listOf(defaultUndeployedShip))

        val areFleetsDeployed = game.areFleetsDeployed()

        assertTrue(areFleetsDeployed)
    }

    @Test
    fun `areFleetsDeployed returns false if no player deployed their fleet`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        val player2 = Player(
            game = game,
            user = User(
                username = "Player 2",
                email = "haram2@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )
        game.addPlayer(player = player)
        game.addPlayer(player = player2)

        val areFleetsDeployed = game.areFleetsDeployed()

        assertFalse(areFleetsDeployed)
    }

    @Test
    fun `areFleetsDeployed returns false if only one player deployed their fleet`() {
        val game = defaultGame
        val player = Player(game = game, user = defaultUser)
        val player2 = Player(
            game = game,
            user = User(
                username = "Player 2",
                email = "haram2@email.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH),
                points = 0
            )
        )
        game.addPlayer(player = player)
        game.addPlayer(player = player2)
        player.deployFleet(listOf(defaultUndeployedShip))

        val areFleetsDeployed = game.areFleetsDeployed()

        assertFalse(areFleetsDeployed)
    }

    @Test
    fun `updatePhase updates the game phase from WAITING_FOR_PLAYERS to DEPLOYING_FLEETS`() {
        val game = defaultGame

        assertEquals(GameState.GamePhase.WAITING_FOR_PLAYERS, game.state.phase)

        game.updatePhase()

        assertEquals(GameState.GamePhase.DEPLOYING_FLEETS, game.state.phase)
    }

    @Test
    fun `updatePhase updates the phase expiration time based on maxTimeForLayoutPhase if new phase is DEPLOYING_FLEETS`() {
        val game = defaultGame

        val phaseExpirationTimeInstantIfBefore = Instant.now().plusSeconds(game.config.maxTimeForLayoutPhase.toLong())

        game.updatePhase()

        val phaseExpirationTimeInstantIfAfter = Instant.now().plusSeconds(game.config.maxTimeForLayoutPhase.toLong())
        val phaseExpirationTimeInstant = game.state.phaseExpirationTime.toInstant()

        assertEquals(GameState.GamePhase.DEPLOYING_FLEETS, game.state.phase)
        assertTrue(
            phaseExpirationTimeInstantIfBefore == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfBefore.isBefore(phaseExpirationTimeInstant)
        )
        assertTrue(
            phaseExpirationTimeInstantIfAfter == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfAfter.isAfter(phaseExpirationTimeInstant)
        )
    }

    @Test
    fun `updatePhase updates the game phase from DEPLOYING_FLEETS to IN_PROGRESS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.DEPLOYING_FLEETS

        assertEquals(GameState.GamePhase.DEPLOYING_FLEETS, game.state.phase)

        game.updatePhase()

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)
    }

    @Test
    fun `updatePhase updates the phase expiration time based on maxTimePerRound if new phase is IN_PROGRESS but old is DEPLOYING_FLEETS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.DEPLOYING_FLEETS

        val phaseExpirationTimeInstantIfBefore = Instant.now().plusSeconds(game.config.maxTimePerRound.toLong())

        game.updatePhase()

        val phaseExpirationTimeInstantIfAfter = Instant.now().plusSeconds(game.config.maxTimePerRound.toLong())
        val phaseExpirationTimeInstant = game.state.phaseExpirationTime.toInstant()

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)
        assertTrue(
            phaseExpirationTimeInstantIfBefore == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfBefore.isBefore(phaseExpirationTimeInstant)
        )
        assertTrue(
            phaseExpirationTimeInstantIfAfter == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfAfter.isAfter(phaseExpirationTimeInstant)
        )
    }

    @Test
    fun `updatePhase updates the game phase from IN_PROGRESS to IN_PROGRESS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.IN_PROGRESS

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)

        game.updatePhase()

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)
    }

    @Test
    fun `updatePhase updates the phase expiration time based on maxTimePerRound if new phase is IN_PROGRESS and old is IN_PROGRESS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.IN_PROGRESS

        val phaseExpirationTimeInstantIfBefore = Instant.now().plusSeconds(game.config.maxTimePerRound.toLong())

        game.updatePhase()

        val phaseExpirationTimeInstantIfAfter = Instant.now().plusSeconds(game.config.maxTimePerRound.toLong())
        val phaseExpirationTimeInstant = game.state.phaseExpirationTime.toInstant()

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)
        assertTrue(
            phaseExpirationTimeInstantIfBefore == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfBefore.isBefore(phaseExpirationTimeInstant)
        )
        assertTrue(
            phaseExpirationTimeInstantIfAfter == phaseExpirationTimeInstant ||
                phaseExpirationTimeInstantIfAfter.isAfter(phaseExpirationTimeInstant)
        )
    }

    @Test
    fun `abortGame changes game phase to FINISHED if previous game phase was WAITING_FOR_PLAYERS`() {
        val game = defaultGame

        assertEquals(GameState.GamePhase.WAITING_FOR_PLAYERS, game.state.phase)

        game.abortGame()

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
    }

    @Test
    fun `abortGame changes game phase to FINISHED if previous game phase was DEPLOYING_FLEETS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.DEPLOYING_FLEETS

        assertEquals(GameState.GamePhase.DEPLOYING_FLEETS, game.state.phase)

        game.abortGame()

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
    }

    @Test
    fun `abortGame changes game phase to FINISHED if previous game phase was IN_PROGRESS`() {
        val game = defaultGame
        game.state.phase = GameState.GamePhase.IN_PROGRESS

        assertEquals(GameState.GamePhase.IN_PROGRESS, game.state.phase)

        game.abortGame()

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
    }

    @Test
    fun `finishGame changes game phase to FINISHED`() {
        val game = defaultGame
        val user = defaultUser
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

        assertEquals(GameState.GamePhase.WAITING_FOR_PLAYERS, game.state.phase)

        game.finishGame(winner = player)

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
    }

    @Test
    fun `finishGame sets winner of the game`() {
        val game = defaultGame
        val user = defaultUser
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

        assertNull(game.state.winner)

        game.finishGame(winner = player)

        assertEquals(player, game.state.winner)
    }

    @Test
    fun `finishGame updates users points`() {
        val game = defaultGame
        val user = defaultUser
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

        val userPoints = user.points
        val user2Points = user2.points
        player.points = 41
        player2.points = 27

        game.finishGame(winner = player)

        assertEquals(userPoints + player.points, user.points)
        assertEquals(user2Points + player2.points, user2.points)
    }

    @Test
    fun `updateIfPhaseExpired does nothing if the game phase isn't expired`() {
        val game = defaultGame
        game.state.phaseExpirationTime = Timestamp.from(Instant.now().plusSeconds(10))

        assertFalse(game.state.phaseExpired())

        val previousPhase = game.state.phase
        val previousWinner = game.state.winner
        val previousTurn = game.state.turn
        val previousRound = game.state.round
        val previousPhaseExpirationTime = game.state.phaseExpirationTime

        game.updateIfPhaseExpired()

        assertEquals(previousPhase, game.state.phase)
        assertEquals(previousWinner, game.state.winner)
        assertEquals(previousTurn, game.state.turn)
        assertEquals(previousRound, game.state.round)
        assertEquals(previousPhaseExpirationTime, game.state.phaseExpirationTime)
    }

    @Test
    fun `updateIfPhaseExpired finishes game if the game phase expired and is IN_PROGRESS`() {
        val game = defaultGame
        val user = defaultUser
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

        game.state.phaseExpirationTime = Timestamp.from(Instant.now().minusSeconds(1))
        game.state.phase = GameState.GamePhase.IN_PROGRESS
        game.state.turn = player

        game.updateIfPhaseExpired()

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
        assertEquals(player2, game.state.winner)
    }

    @Test
    fun `updateIfPhaseExpired aborts game if the game phase expired and is not IN_PROGRESS`() {
        val game = defaultGame
        val user = defaultUser
        val player = Player(game = game, user = user)
        game.addPlayer(player = player)

        game.state.phaseExpirationTime = Timestamp.from(Instant.now().minusSeconds(1))
        game.state.phase = GameState.GamePhase.WAITING_FOR_PLAYERS

        game.updateIfPhaseExpired()

        assertEquals(GameState.GamePhase.FINISHED, game.state.phase)
        assertNull(game.state.winner)
    }

    companion object {
        val defaultGame
            get() = Game(
                name = "name",
                creator = defaultUser,
                config = defaultGameConfig,
                state = defaultGameState
            )
    }
}
