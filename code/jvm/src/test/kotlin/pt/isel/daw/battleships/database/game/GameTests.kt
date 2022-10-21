package pt.isel.daw.battleships.database.game

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pt.isel.daw.battleships.database.DatabaseTest
import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState
import pt.isel.daw.battleships.database.model.ship.ShipType
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.database.repositories.games.GamesRepository
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GameTests : DatabaseTest() {
    @Autowired
    lateinit var gamesRepository: GamesRepository

    @Autowired
    lateinit var usersRepository: UsersRepository

    val defaultGameConfig = GameConfig(
        gridSize = 10,
        maxTimePerRound = 60,
        shotsPerRound = 1,
        maxTimeForLayoutPhase = 60,
        shipTypes = listOf(
            ShipType("Carrier", 1, 5, 50),
            ShipType("Battleship", 1, 4, 40),
            ShipType("Cruiser", 2, 3, 30),
            ShipType("Submarine", 2, 3, 30),
            ShipType("Destroyer", 3, 2, 20)
        )
    )

    val defaultGameConfigWithMinorError = GameConfig(
        gridSize = 10,
        maxTimePerRound = 60,
        shotsPerRound = 1,
        maxTimeForLayoutPhase = 60,
        shipTypes = listOf(
            ShipType("Carrier", 1, 5, 50),
            ShipType("Battleship", 1, 4, 40),
            ShipType("Cruiser", 2, 3, 30),
            ShipType("Submarine", 2, 3, 30),
            ShipType("Destroyer", 3, 2, 19)
        )
    )

    @Test
    fun `Test find by game phase with existing player`() {
        val user = this.usersRepository.save(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        val game = Game(
            name = "test",
            creator = user,
            config = defaultGameConfig,
            state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
        )

        game.addPlayer(
            player = Player(game = game, user = user)
        )

        this.gamesRepository.save(game)

        val availableGame = this.gamesRepository.findFirstAvailableGameWithConfig(user, defaultGameConfig)

        assertNull(availableGame)
    }

    @Test
    fun `Test find by game phase with new user`() {
        val user = this.usersRepository.save(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        val game = Game(
            name = "test",
            creator = user,
            config = defaultGameConfig,
            state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
        )

        game.addPlayer(
            player = Player(game = game, user = user)
        )

        this.gamesRepository.save(game)

        val newUser = this.usersRepository.save(
            User(
                username = "bob2",
                email = "bob2@bob2.com",
                passwordHash = "passwordHash"
            )
        )

        val availableGame = this.gamesRepository.findFirstAvailableGameWithConfig(newUser, defaultGameConfig)

        assertNotNull(availableGame)
    }

    @Test
    fun `Test find by game phase with new user and wrong config`() {
        val user = this.usersRepository.save(
            User(
                username = "bob",
                email = "bob@bob.com",
                passwordHash = "passwordHash"
            )
        )

        val game = Game(
            name = "test",
            creator = user,
            config = defaultGameConfig,
            state = GameState(GameState.GamePhase.WAITING_FOR_PLAYERS)
        )

        game.addPlayer(
            player = Player(game = game, user = user)
        )

        this.gamesRepository.save(game)

        val newUser = this.usersRepository.save(
            User(
                username = "bob2",
                email = "bob2@bob2.com",
                passwordHash = "passwordHash"
            )
        )

        val availableGame =
            this.gamesRepository.findFirstAvailableGameWithConfig(newUser, defaultGameConfigWithMinorError)

        assertNull(availableGame)
    }
}
