package pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.UndeployedShipModel
import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedFleetDTO
import javax.validation.constraints.Size

/**
 * Represents the input data for the deployment fleet operation.
 *
 * @property ships the list of ships to be deployed
 */
data class DeployFleetInputModel(
    @Size(min = MIN_SHIPS_COUNT, message = "The fleet must have at least $MIN_SHIPS_COUNT ships")
    val ships: List<UndeployedShipModel>
) {
    fun toUndeployedFleetDTO() = UndeployedFleetDTO(
        ships = ships.map { it.toUndeployedShipDTO() }
    )

    companion object {
        private const val MIN_SHIPS_COUNT = 1
    }
}
