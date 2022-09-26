package pt.isel.daw.battleships.api.games.dtos.shot

import pt.isel.daw.battleships.api.games.dtos.CoordinateDTO

/**
 * Represents a shot in the game.
 *
 * @property coordinate The coordinate of the shot.
 * @property result The result of the shot.
 */
data class ShotDTO(
    val coordinate: CoordinateDTO,
    val result: String
)
