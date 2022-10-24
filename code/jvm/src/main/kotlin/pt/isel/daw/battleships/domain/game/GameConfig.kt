package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.ship.ShipType
import pt.isel.daw.battleships.domain.ship.UndeployedShip
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

/**
 * The GameConfig entity.
 *
 * @property gridSize the grid size.
 * @property maxTimePerRound the maximum time per round
 * @property shotsPerRound the shots per round
 * @property maxTimeForLayoutPhase the maximum time for layout phase
 * @property shipTypes the ship types
 * @property colsRange the columns range
 * @property rowsRange the rows range
 */
@Embeddable
class GameConfig(
    @Column(name = "grid_size", nullable = false)
    val gridSize: Int,

    @Column(name = "max_time_per_round", nullable = false)
    val maxTimePerRound: Int,

    @Column(name = "max_time_for_layout_phase", nullable = false)
    val maxTimeForLayoutPhase: Int,

    @Column(name = "shots_per_round", nullable = false)
    val shotsPerRound: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "game")
    val shipTypes: List<ShipType>
) {
    val colsRange
        get() = 'A' until 'A' + gridSize

    val rowsRange
        get() = 1..gridSize

    /**
     * Validates a given fleet ship types against this configuration.
     *
     * The fleet is valid if:
     * - It has the same number of ships as the configuration
     * - Each shiptype in the configuration has the same number of ships in the fleet
     *
     * @param ships the ships to validate
     * @return true if the fleet is valid, false otherwise
     */
    fun testShipTypes(ships: List<UndeployedShip>): Boolean =
        shipTypes.sumOf { shipType -> shipType.quantity } == ships.size &&
            shipTypes.all { shipType -> shipType.quantity == ships.count { shipType == it.type } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameConfig

        if (gridSize != other.gridSize) return false
        if (maxTimePerRound != other.maxTimePerRound) return false
        if (shotsPerRound != other.shotsPerRound) return false
        if (maxTimeForLayoutPhase != other.maxTimeForLayoutPhase) return false
        if (shipTypes.size != other.shipTypes.size) return false
        if (shipTypes.toSet() == other.shipTypes.toSet()) return false
        if (shipTypes.any { shipType ->
            other.shipTypes.none { otherShipType ->
                shipType == otherShipType
            }
        }
        ) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gridSize
        result = 31 * result + maxTimePerRound
        result = 31 * result + shotsPerRound
        result = 31 * result + maxTimeForLayoutPhase
        result = 31 * result + shipTypes.hashCode()
        return result
    }
}