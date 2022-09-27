package pt.isel.daw.battleships.api.games.dtos.game.createGame

/**
 * Represents the response body of a game creation request.
 *
 * @property gameId The id of the created game.
 */
data class CreateGameResponseDTO(
    val gameId: Int
)
