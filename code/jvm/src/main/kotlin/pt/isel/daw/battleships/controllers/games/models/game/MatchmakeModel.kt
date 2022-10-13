package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO

/**
 * Represents a matchmake.
 *
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeModel(
    val wasCreated: Boolean
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(matchmakeDTO.wasCreated)
}
