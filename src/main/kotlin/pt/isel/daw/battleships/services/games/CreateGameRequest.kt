package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.database.model.Player

/**
 * Represents a game creation request.
 */
data class CreateGameRequest(
    val player1: Player
)
