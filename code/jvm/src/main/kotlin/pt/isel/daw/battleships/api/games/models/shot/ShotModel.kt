package pt.isel.daw.battleships.api.games.models.shot

import pt.isel.daw.battleships.api.games.models.CoordinateModel

/**
 * Represents a shot in the game.
 *
 * @property shotNumber the number of the shot.
 * @property coordinate The coordinate of the shot.
 * @property result The result of the shot.
 */
data class ShotModel(
    val shotNumber: Int,
    val coordinate: CoordinateModel,
    val result: String
)
