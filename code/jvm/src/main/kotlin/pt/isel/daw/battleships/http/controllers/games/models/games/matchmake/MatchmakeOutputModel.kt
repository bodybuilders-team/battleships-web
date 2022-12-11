package pt.isel.daw.battleships.http.controllers.games.models.games.matchmake

import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTO

/**
 * A Matchmake Output Model.
 *
 * @property gameId the id of the game to which the player was matched
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeOutputModel(
    val gameId: Int,
    val wasCreated: Boolean
) {
    constructor(matchmakeResponseDTO: MatchmakeResponseDTO) : this(
        gameId = matchmakeResponseDTO.game.id,
        wasCreated = matchmakeResponseDTO.wasCreated
    )
}
