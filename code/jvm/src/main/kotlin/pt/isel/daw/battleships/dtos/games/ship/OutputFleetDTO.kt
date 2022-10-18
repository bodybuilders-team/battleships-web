package pt.isel.daw.battleships.dtos.games.ship

/**
 * Represents a list of Output Ship DTOs.
 *
 * @property ships the list of ship DTOs
 */
data class OutputFleetDTO(
    val ships: List<OutputShipDTO>
)
