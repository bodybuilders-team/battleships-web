package pt.isel.daw.battleships.service.games.dtos.ship

/**
 * A Deployed Fleet DTO.
 *
 * @property ships the list of ship DTOs
 */
data class DeployedFleetDTO(
    val ships: List<DeployedShipDTO>
)
