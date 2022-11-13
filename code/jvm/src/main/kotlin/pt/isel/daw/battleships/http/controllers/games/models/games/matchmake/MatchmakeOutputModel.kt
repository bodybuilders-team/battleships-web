package pt.isel.daw.battleships.http.controllers.games.models.games.matchmake

import pt.isel.daw.battleships.service.games.dtos.game.MatchmakeResponseDTO

/**
 * A Matchmake Output Model.
 *
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeOutputModel(
    val wasCreated: Boolean
) {
    constructor(matchmakeResponseDTO: MatchmakeResponseDTO) : this(
        wasCreated = matchmakeResponseDTO.wasCreated
    )
}
