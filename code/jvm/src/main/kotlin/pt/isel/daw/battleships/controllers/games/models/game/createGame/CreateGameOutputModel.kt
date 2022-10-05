package pt.isel.daw.battleships.controllers.games.models.game.createGame

/**
 * Represents the response body of a game creation request.
 *
 * @property gameId the id of the created game
 */
data class CreateGameOutputModel(
    val gameId: Int
)
