package pt.isel.daw.battleships.http.controllers.games.models.players.shot

/**
 * A Fired Shots Model.
 *
 * @property shots the list of shots created
 */
data class FiredShotsModel(
    val shots: List<FiredShotModel>
)
