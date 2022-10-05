package pt.isel.daw.battleships.api.games.models.game.createGame

/**
 * Represents the response body of a game creation request.
 *
 * @property gameId The id of the created game.
 */
data class CreateGameOutputModel(
    val gameId: Int
)