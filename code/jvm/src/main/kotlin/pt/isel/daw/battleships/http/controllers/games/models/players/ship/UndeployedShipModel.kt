package pt.isel.daw.battleships.http.controllers.games.models.players.ship

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.http.controllers.games.models.ShipTypeModel.Companion.MAX_SHIP_NAME_LENGTH
import pt.isel.daw.battleships.http.controllers.games.models.ShipTypeModel.Companion.MIN_SHIP_NAME_LENGTH
import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedShipDTO
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * An Undeployed Ship Model.
 *
 * @property type type of the ship
 * @property coordinate coordinate of the ship
 * @property orientation orientation of the ship
 */
data class UndeployedShipModel(
    @field:Size(
        min = MIN_SHIP_NAME_LENGTH,
        max = MAX_SHIP_NAME_LENGTH,
        message = "Ship type name must be between $MIN_SHIP_NAME_LENGTH and $MAX_SHIP_NAME_LENGTH characters long"
    )
    val type: String,

    val coordinate: CoordinateModel,

    @field:Pattern(regexp = ORIENTATION_REGEX, message = "Orientation must be either HORIZONTAL or VERTICAL")
    val orientation: String
) {

    /**
     * Converts the input ship model to a DTO.
     *
     * @return the input ship DTO
     */
    fun toUndeployedShipDTO() = UndeployedShipDTO(
        type = type,
        coordinate = coordinate.toCoordinateDTO(),
        orientation = orientation
    )

    companion object {
        const val ORIENTATION_REGEX = "HORIZONTAL|VERTICAL"
    }
}
