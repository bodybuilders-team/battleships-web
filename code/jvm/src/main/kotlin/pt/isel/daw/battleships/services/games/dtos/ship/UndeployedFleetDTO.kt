package pt.isel.daw.battleships.services.games.dtos.ship

/**
 * Represents a list of Input Ship DTOs.
 *
 * @property ships the list of ship DTOs
 */
data class UndeployedFleetDTO(
    val ships: List<UndeployedShipDTO>
)
