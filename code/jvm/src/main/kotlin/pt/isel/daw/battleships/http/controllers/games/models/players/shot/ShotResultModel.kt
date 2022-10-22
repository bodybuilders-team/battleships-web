package pt.isel.daw.battleships.http.controllers.games.models.players.shot

import pt.isel.daw.battleships.service.games.dtos.shot.ShotResultDTO

/**
 * Represents a model for a shot result.
 *
 * @property result the result of the shot
 */
data class ShotResultModel(
    val result: String
) {

    constructor(shotResultDTO: ShotResultDTO) : this(
        result = shotResultDTO.result
    )

    /**
     * Converts the shot result model to a DTO.
     *
     * @return the shot result DTO
     */
    fun toShotResultDTO() = ShotResultDTO(
        result = result
    )
}
