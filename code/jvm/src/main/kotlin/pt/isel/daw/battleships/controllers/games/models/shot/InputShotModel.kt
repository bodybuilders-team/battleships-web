package pt.isel.daw.battleships.controllers.games.models.shot

import pt.isel.daw.battleships.controllers.games.models.ship.CoordinateModel
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotDTO

/**
 * Represents a shot in the game.
 *
 * @property coordinate the coordinate of the shot
 */
data class InputShotModel(val coordinate: CoordinateModel) {
    fun toInputShotDTO() = InputShotDTO(coordinate.toCoordinateDTO())
}
