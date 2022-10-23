package pt.isel.daw.battleships.service.games.dtos.game

/**
 * Represents a Matchmake Response DTO.
 *
 * @property game the game of the matchmake
 * @property wasCreated true if the game was created, false if it was joined
 */
data class MatchmakeResponseDTO(
    val game: GameDTO,
    val wasCreated: Boolean
)
