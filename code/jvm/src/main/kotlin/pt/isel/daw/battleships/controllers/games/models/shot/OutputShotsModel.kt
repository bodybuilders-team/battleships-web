package pt.isel.daw.battleships.controllers.games.models.shot

/**
 * Represents the output data for the create shots operation.
 *
 * @property shots the list of shots created
 */
data class OutputShotsModel(val shots: List<OutputShotModel>)
