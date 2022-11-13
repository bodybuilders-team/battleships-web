package pt.isel.daw.battleships.http.controllers.games.models.players.getFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel

/**
 * A Get Fleet Output Model.
 *
 * @property ships the list of ships
 */
data class GetFleetOutputModel(
    val ships: List<DeployedShipModel>
)
