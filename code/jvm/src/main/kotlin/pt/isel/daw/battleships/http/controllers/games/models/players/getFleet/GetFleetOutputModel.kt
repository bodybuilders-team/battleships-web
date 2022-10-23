package pt.isel.daw.battleships.http.controllers.games.models.players.getFleet

import pt.isel.daw.battleships.http.controllers.games.models.players.ship.DeployedShipModel

/**
 * Represents a Get Fleet Output Model.
 *
 * @property ships the list of ships
 */
data class GetFleetOutputModel(
    val ships: List<DeployedShipModel>
)
