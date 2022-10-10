package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.Player
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

/**
 * The GameState entity.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the current round
 * @property turn the current player
 * @property winner the winner player
 */
@Embeddable
class GameState(
    @Column(name = "phase", nullable = false)
    @Enumerated(EnumType.STRING)
    var phase: GamePhase = GamePhase.WAITING_FOR_PLAYERS,

    @Column(name = "phase_end_time", nullable = false)
    var phaseEndTime: Timestamp = Timestamp(System.currentTimeMillis() + MATCHMAKING_MAX_TIME),

    @Column(name = "round", nullable = false)
    val round: Int? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "turn", nullable = true)
    val turn: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = true)
    var winner: Player? = null
) : Serializable {

    /**
     * Represents the game phases.
     */
    enum class GamePhase {
        WAITING_FOR_PLAYERS,
        PLACING_SHIPS,
        IN_PROGRESS,
        FINISHED
    }

    companion object {
        private const val MATCHMAKING_MAX_TIME = 60 * 1000L
    }
}
