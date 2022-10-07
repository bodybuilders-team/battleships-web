package pt.isel.daw.battleships.controllers.games.models.ship

/**
 * Represents the out data for the deploy fleet operation.
 *
 * @property successfullyDeployed if the fleet was successfully deployed
 */
data class DeployFleetOutputModel(val successfullyDeployed: Boolean)