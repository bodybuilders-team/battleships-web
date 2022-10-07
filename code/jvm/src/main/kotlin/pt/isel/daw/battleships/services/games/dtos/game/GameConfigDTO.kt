package pt.isel.daw.battleships.services.games.dtos.game

import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.services.games.dtos.ship.ShipTypeDTO

/**
 * Represents a Game Config DTO.
 *
 * @property gridSize the size of the grid
 * @property maxTimePerShot the maximum time per shot
 * @property shotsPerRound the number of shots per round
 * @property maxTimeForLayoutPhase the maximum time for the layout phase
 * @property shipTypes the ship types allowed in the game
 */
data class GameConfigDTO(
    val gridSize: Int,
    val maxTimeForLayoutPhase: Int,
    val shotsPerRound: Int,
    val maxTimePerShot: Int,
    val shipTypes: List<ShipTypeDTO>
) {
    constructor(gridSize: GameConfig) : this(
        gridSize = gridSize.gridSize,
        maxTimeForLayoutPhase = gridSize.maxTimeForLayoutPhase,
        shotsPerRound = gridSize.shotsPerRound,
        maxTimePerShot = gridSize.maxTimePerShot,
        shipTypes = gridSize.shipTypes.map { ShipTypeDTO(it) }
    )

    /**
     * Converts this DTO to a database model.
     *
     * @return the database model game config
     */
    fun toGameConfig(): GameConfig =
        GameConfig(
            gridSize = gridSize,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shotsPerRound = shotsPerRound,
            maxTimePerShot = maxTimePerShot,
            shipTypes = shipTypes.map { it.toShipType() }
        )
}
