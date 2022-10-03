package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.ship.ShipType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

/**
 * The GameState entity.
 *
 * @property game The game id.
 * @property gridSize The grid size.
 * @property maxTimePerShot The maximum time per shot.
 * @property shotsPerRound The shots per round.
 * @property maxTimeForLayoutPhase The maximum time for layout phase.
 * @property shipTypes The ship types.
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
)
