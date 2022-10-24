package pt.isel.daw.battleships.service.games.dtos.shot

import pt.isel.daw.battleships.service.games.dtos.CoordinateDTO

/**
 * Represents an Unfired Shot DTO.
 *
 * @property coordinate the coordinate of the shot
 */
data class UnfiredShotDTO(
    val coordinate: CoordinateDTO
)