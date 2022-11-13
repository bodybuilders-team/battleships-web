package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.http.controllers.games.models.CoordinateModel
import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotDTO

/**
 * An Unfired Shot Model.
 *
 * @property coordinate the coordinate of the shot
 */
data class UnfiredShotModel(
    val coordinate: CoordinateModel
) {

    /**
     * Converts the input shot model to a DTO.
     *
     * @return the input shot DTO
     */
    fun toUnfiredShotDTO() = UnfiredShotDTO(
        coordinate = coordinate.toCoordinateDTO()
    )
}
