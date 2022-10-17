package pt.isel.daw.battleships.http.controllers.games.models.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.ship.OutputShipModel.Companion.ORIENTATION_REGEX
import pt.isel.daw.battleships.http.controllers.games.models.ship.ShipTypeModel.Companion.MAX_SHIP_NAME_LENGTH
import pt.isel.daw.battleships.http.controllers.games.models.ship.ShipTypeModel.Companion.MIN_SHIP_NAME_LENGTH
import pt.isel.daw.battleships.services.games.dtos.ship.InputShipDTO
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * Represents a model for a ship creation request.
 *
 * @property type type of the ship
 * @property coordinate coordinate of the ship
 * @property orientation orientation of the ship
 */
data class InputShipModel(
    @Size(
        min = MIN_SHIP_NAME_LENGTH,
        max = MAX_SHIP_NAME_LENGTH,
        message = "Ship type name must be between $MIN_SHIP_NAME_LENGTH and $MAX_SHIP_NAME_LENGTH characters long"
    )
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = ORIENTATION_REGEX, message = "Orientation must be either HORIZONTAL or VERTICAL")
    val orientation: String
) {

    /**
     * Converts the input ship model to a DTO.
     *
     * @return the input ship DTO
     */
    fun toInputShipDTO() = InputShipDTO(
        type = type,
        coordinate = coordinate.toCoordinateDTO(),
        orientation = orientation
    )
}
