package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.InputShipDTO
import javax.validation.constraints.Pattern

/**
 * Represents a model for a ship creation request.
 *
 * @property type type of the ship
 * @property coordinate coordinate of the ship
 * @property orientation orientation of the ship
 */
data class InputShipModel(
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = "[HV]")
    val orientation: Char?
) {

    /**
     * Converts the input ship model to a DTO.
     *
     * @return the input ship DTO
     */
    fun toInputShipDTO() = InputShipDTO(type, coordinate.toCoordinateDTO(), orientation)
}
