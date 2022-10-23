package pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet

/**
 * Represents a Deploy Fleet Output Model.
 *
 * @property successfullyDeployed if the fleet was successfully deployed
 */
data class DeployFleetOutputModel(
    val successfullyDeployed: Boolean
)
