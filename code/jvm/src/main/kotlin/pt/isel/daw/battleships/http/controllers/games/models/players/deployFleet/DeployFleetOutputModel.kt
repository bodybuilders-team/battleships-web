package pt.isel.daw.battleships.http.controllers.games.models.players.deployFleet

/**
 * Represents the out data for the deployment fleet operation.
 *
 * @property successfullyDeployed if the fleet was successfully deployed
 */
data class DeployFleetOutputModel(
    val successfullyDeployed: Boolean
)
