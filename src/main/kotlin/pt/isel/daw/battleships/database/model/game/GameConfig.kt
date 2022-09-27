package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.ship.ShipType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

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
@Entity
@Table(name = "gameconfigs")
class GameConfig(
    @Id
    @OneToOne
    @JoinColumn(name = "game_id")
    val game: Game,

    @Column(name = "grid_size", nullable = false)
    val gridSize: Int,

    @Column(name = "max_time_per_shot", nullable = false)
    val maxTimePerShot: Int,

    @Column(name = "shots_per_round", nullable = false)
    val shotsPerRound: Int,

    @Column(name = "max_time_for_layout_phase", nullable = false)
    val maxTimeForLayoutPhase: Int,

    @ManyToMany(cascade = [CascadeType.REMOVE])
    @JoinTable(
        name = "gameconfig_shiptypes",
        joinColumns = [JoinColumn(name = "game_id")],
        inverseJoinColumns = [JoinColumn(name = "ship_name")]
    )
    val shipTypes: List<ShipType>
)
