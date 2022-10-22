package pt.isel.daw.battleships.services.games.dtos.game

/**
 * Represents a Matchmake DTO.
 *
 * @property game the game of the matchmake
 * @property wasCreated true if the game was created, false if it was joined
 */
data class MatchmakeDTO(
    val game: GameDTO,
    val wasCreated: Boolean
)
