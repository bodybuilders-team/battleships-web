package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Game

/**
 * Represents a response with a game.
 *
 * @property game the game response
 */
data class GameResponse(
    val game: Game
)
