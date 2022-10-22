package pt.isel.daw.battleships.http.controllers.games.models.players.shot

/**
 * Represents the output data for the create shots operation.
 *
 * @property shots the list of shots created
 */
data class FiredShotsModel(
    val shots: List<FiredShotModel>
)
