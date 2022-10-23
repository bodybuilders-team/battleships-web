package pt.isel.daw.battleships.service.games.dtos.ship

/**
 * Represents an Undeployed Fleet DTO.
 *
 * @property ships the list of ship DTOs
 */
data class UndeployedFleetDTO(
    val ships: List<UndeployedShipDTO>
)
