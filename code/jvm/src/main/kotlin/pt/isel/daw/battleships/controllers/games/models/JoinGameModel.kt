package pt.isel.daw.battleships.controllers.games.models

/**
 * Represents the response to the request to join a game.
 *
 * @property gameId the id of the joined game
 */
data class JoinGameModel(
    val gameId: Int
)
