package pt.isel.daw.battleships.repository.games

import org.springframework.beans.factory.annotation.Autowired
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.domain.games.game.GameConfigTests.Companion.defaultGameConfig
import pt.isel.daw.battleships.domain.games.game.GameState
import pt.isel.daw.battleships.domain.games.ship.ShipType
import pt.isel.daw.battleships.domain.users.User
import pt.isel.daw.battleships.testUtils.DatabaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GamesRepositoryTests : DatabaseTest() {
    @Autowired
    lateinit var gamesRepository: GamesRepository

    @Test
    fun `findById returns game with the given ID`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val game = entityManager.persist(
            Game(
                name = "test1",
                creator = user,
                config = defaultGameConfig,
                state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
            )
        )

        val foundGame = gamesRepository.findById(game.id!!)

        assertNotNull(foundGame)
        assertEquals(game, foundGame)
    }

    @Test
    fun `findById returns null if a game with the given ID is not found`() {
        val foundGame = gamesRepository.findById(id = 2)

        assertNull(foundGame)
    }

    @Test
    fun `findAllByStatePhase returns all the games with the given phase`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val validGames = List(2) {
            entityManager.persist(
                Game(
                    name = "test$it",
                    creator = user,
                    config = defaultGameConfig,
                    state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
                )
            )
        }

        entityManager.persist(
            Game(
                name = "test2",
                creator = user,
                config = defaultGameConfig,
                state = GameState(GameState.GamePhase.DEPLOYING_FLEETS)
            )
        )

        val foundGames = gamesRepository.findAllByStatePhase(GameState.GamePhase.WAITING_FOR_PLAYERS).toList()

        assertEquals(2, foundGames.size)
        assertEquals(validGames, foundGames.toList())
    }

    @Test
    fun `findAllAvailableGamesWithConfig returns all the available games with the given configuration`() {
        val user = entityManager.persist(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "a".repeat(User.PASSWORD_HASH_LENGTH)
            )
        )

        val validGames = List(2) {
            entityManager.persist(
                Game(
                    name = "test$it",
                    creator = user,
                    config = defaultGameConfig,
                    state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
                )
            )
        }

        entityManager.persist(
            Game(
                name = "test3",
                creator = user,
                config = GameConfig(
                    gridSize = 10,
                    maxTimePerRound = 60,
                    shotsPerRound = 1,
                    maxTimeForLayoutPhase = 60,
                    shipTypes = listOf(
                        ShipType("Carrier", 1, 5, 50)
                    )
                ),
                state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
            )
        )

        val availableGames = gamesRepository.findAllAvailableGamesWithConfig(defaultGameConfig).toList()

        assertEquals(2, availableGames.size)
        assertEquals(validGames, availableGames.toList())
    }
}
