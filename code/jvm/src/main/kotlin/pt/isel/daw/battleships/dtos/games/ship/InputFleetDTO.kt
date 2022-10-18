package pt.isel.daw.battleships.dtos.games.ship

/**
 * Represents a list of Input Ship DTOs.
 *
 * @property ships the list of ship DTOs
 */
data class InputFleetDTO(
    val ships: List<InputShipDTO>
)
