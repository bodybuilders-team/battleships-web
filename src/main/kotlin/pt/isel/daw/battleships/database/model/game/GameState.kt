package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.Player
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * The GameState entity.
 *
 * @property game The game id.
 * @property round The current round.
 * @property turn The current player.
 * @property winner The winner player.
 */
@Entity
@Table(name = "gamestates")
class GameState(
    @Id
    @OneToOne
    @JoinColumn(name = "game_id")
    val game: Game,

    @Column(name = "phase", nullable = false)
    @Enumerated(EnumType.STRING)
    val phase: GamePhase = GamePhase.WAITING_FOR_PLAYERS,

    @Column(name = "round", nullable = true)
    val round: Int = 0,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "turn", nullable = true)
    val turn: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = true)
    val winner: Player? = null
) {

    /**
     * Represents the game phases.
     */
    enum class GamePhase {
        WAITING_FOR_PLAYERS,
        PLACING_SHIPS,
        IN_PROGRESS,
        FINISHED
    }
}
