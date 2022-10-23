package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.game.GameState.GamePhase.DEPLOYING_FLEETS
import pt.isel.daw.battleships.domain.game.GameState.GamePhase.FINISHED
import pt.isel.daw.battleships.domain.game.GameState.GamePhase.IN_PROGRESS
import pt.isel.daw.battleships.domain.game.GameState.GamePhase.WAITING_FOR_PLAYERS
import java.io.Serializable
import java.sql.Timestamp
import java.time.Instant
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
 * @property phaseExpirationTime the time when the current phase ends
 * @property round the current round
 * @property turn the current player
 * @property winner the winner player
 */
@Embeddable
class GameState(
    @Column(name = "phase", nullable = false)
    @Enumerated(EnumType.STRING)
    var phase: GamePhase = WAITING_FOR_PLAYERS,

    @Column(name = "phase_expiration_time", nullable = false)
    var phaseExpirationTime: Timestamp = Timestamp.from(Instant.now().plusSeconds(MATCHMAKING_MAX_TIME)),

    @Column(name = "round", nullable = false)
    var round: Int? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "turn", nullable = true)
    var turn: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = true)
    var winner: Player? = null
) : Serializable {

    /**
     * Checks if the game phase has expired.
     *
     * @return true if the game phase has expired, false otherwise
     */
    fun phaseExpired(): Boolean =
        phaseExpirationTime.time < System.currentTimeMillis() &&
            phase != FINISHED

    /**
     * Represents the game phases.
     *
     * @property WAITING_FOR_PLAYERS the game is waiting for players to join
     * @property DEPLOYING_FLEETS the game is waiting for players to place their ships
     * @property IN_PROGRESS the game is in progress
     * @property FINISHED the game is finished
     */
    enum class GamePhase {
        WAITING_FOR_PLAYERS,
        DEPLOYING_FLEETS,
        IN_PROGRESS,
        FINISHED;

        /**
         * Gets the next game phase.
         *
         * @return the next game phase
         */
        fun next(): GamePhase =
            when (this) {
                WAITING_FOR_PLAYERS -> DEPLOYING_FLEETS
                DEPLOYING_FLEETS -> IN_PROGRESS
                IN_PROGRESS -> FINISHED
                FINISHED -> throw IllegalStateException("Cannot advance from FINISHED phase.")
            }
    }

    companion object {
        private const val MATCHMAKING_MAX_TIME = 60L
    }
}
