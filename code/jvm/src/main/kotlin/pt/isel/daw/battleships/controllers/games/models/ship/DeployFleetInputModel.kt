package pt.isel.daw.battleships.controllers.games.models.ship

/**
 * Represents the input data for the deploy fleet operation.
 *
 * @property ships the list of ships to be deployed
 */
data class DeployFleetInputModel(val ships: List<InputShipModel>)
