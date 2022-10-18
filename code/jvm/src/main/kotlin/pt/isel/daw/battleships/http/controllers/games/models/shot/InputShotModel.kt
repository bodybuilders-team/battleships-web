package pt.isel.daw.battleships.http.controllers.games.models.shot

import pt.isel.daw.battleships.dtos.games.shot.InputShotDTO
import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel

/**
 * Represents a shot in the game.
 *
 * @property coordinate the coordinate of the shot
 */
data class InputShotModel(val coordinate: CoordinateModel) {

    /**
     * Converts the input shot model to a DTO.
     *
     * @return the input shot DTO
     */
    fun toInputShotDTO() = InputShotDTO(coordinate = coordinate.toCoordinateDTO())
}
