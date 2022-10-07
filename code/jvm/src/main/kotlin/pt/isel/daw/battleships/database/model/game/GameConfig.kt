package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.ship.ShipType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

/**
 * The GameConfig entity.
 *
 * @property gridSize the grid size.
 * @property maxTimePerShot the maximum time per shot
 * @property shotsPerRound the shots per round
 * @property maxTimeForLayoutPhase the maximum time for layout phase
 * @property shipTypes the ship types
 */
@Embeddable
class GameConfig(
    @Column(name = "grid_size", nullable = false)
    val gridSize: Int,

    @Column(name = "max_time_per_shot", nullable = false)
    val maxTimePerShot: Int,

    @Column(name = "shots_per_round", nullable = false)
    val shotsPerRound: Int,

    @Column(name = "max_time_for_layout_phase", nullable = false)
    val maxTimeForLayoutPhase: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "game")
    val shipTypes: List<ShipType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameConfig

        if (gridSize != other.gridSize) return false
        if (maxTimePerShot != other.maxTimePerShot) return false
        if (shotsPerRound != other.shotsPerRound) return false
        if (maxTimeForLayoutPhase != other.maxTimeForLayoutPhase) return false
        if (shipTypes.size != other.shipTypes.size) return false
        if (shipTypes.toSet() == other.shipTypes.toSet()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gridSize
        result = 31 * result + maxTimePerShot
        result = 31 * result + shotsPerRound
        result = 31 * result + maxTimeForLayoutPhase
        result = 31 * result + shipTypes.hashCode()
        return result
    }
}
