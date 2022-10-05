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
    val maxTimePerShot: Int,
    val shotsPerRound: Int,
    val maxTimeForLayoutPhase: Int,
    val shipTypes: List<ShipTypeDTO>
) {
    constructor(gridSize: GameConfig) : this(
        gridSize = gridSize.gridSize,
        maxTimePerShot = gridSize.maxTimePerShot,
        shotsPerRound = gridSize.shotsPerRound,
        maxTimeForLayoutPhase = gridSize.maxTimeForLayoutPhase,
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
            maxTimePerShot = maxTimePerShot,
            shotsPerRound = shotsPerRound,
            maxTimeForLayoutPhase = maxTimeForLayoutPhase,
            shipTypes = shipTypes.map { it.toShipType() }
        )
}
