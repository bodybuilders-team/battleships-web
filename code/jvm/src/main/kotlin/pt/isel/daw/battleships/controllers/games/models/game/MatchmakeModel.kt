package pt.isel.daw.battleships.controllers.games.models.game

import pt.isel.daw.battleships.services.games.dtos.game.MatchmakeDTO

/**
 * Represents a matchmake.
 *
 * @property game the game that the player was matched to
 * @property wasCreated whether the game was created or not
 */
data class MatchmakeModel(
    val game: GameModel,
    val wasCreated: Boolean
) {
    constructor(matchmakeDTO: MatchmakeDTO) : this(
        GameModel(matchmakeDTO.game),
        matchmakeDTO.wasCreated
    )
}
