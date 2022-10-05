package pt.isel.daw.battleships.controllers.games.models.ship

import pt.isel.daw.battleships.services.games.dtos.ship.InputShipDTO
import javax.validation.constraints.Pattern

data class InputShipModel(
    val type: String,
    val coordinate: CoordinateModel,

    @Pattern(regexp = "[HV]")
    val orientation: Char?
) {
    fun toDTO() = InputShipDTO(type, coordinate.toCoordinateDTO(), orientation)
}