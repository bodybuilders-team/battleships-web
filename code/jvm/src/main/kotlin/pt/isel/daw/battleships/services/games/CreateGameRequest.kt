package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.game.GameConfig

/**
 * Represents a game creation request.
 */
data class CreateGameRequest(
    val name: String,
    val player1: Player,
    val config: GameConfig
)
