package pt.isel.daw.battleships.http.controllers.games.models.game

import pt.isel.daw.battleships.dtos.games.game.MatchmakeDTO

/**
 * Represents a matchmake.
 *
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeModel(
    val wasCreated: Boolean
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(wasCreated = matchmakeDTO.wasCreated)
}
