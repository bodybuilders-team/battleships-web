package pt.isel.daw.battleships.domain.games.game

import pt.isel.daw.battleships.domain.exceptions.InvalidGameStateException
import pt.isel.daw.battleships.domain.games.Player
import pt.isel.daw.battleships.domain.games.game.GameState.GamePhase.DEPLOYING_FLEETS
import pt.isel.daw.battleships.domain.games.game.GameState.GamePhase.FINISHED
import pt.isel.daw.battleships.domain.games.game.GameState.GamePhase.IN_PROGRESS
import pt.isel.daw.battleships.domain.games.game.GameState.GamePhase.WAITING_FOR_PLAYERS
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
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
class GameState {
    @Column(name = "phase", nullable = false)
    @Enumerated(EnumType.STRING)
    var phase: GamePhase

    @Column(name = "phase_expiration_time", nullable = false)
    var phaseExpirationTime: Timestamp

    @Column(name = "round", nullable = false)
    var round: Int?

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "turn", nullable = true)
    var turn: Player?

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = true)
    var winner: Player?

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(
        phase: GamePhase = WAITING_FOR_PLAYERS,
        phaseExpirationTime: Timestamp = Timestamp.from(Instant.now().plus(MATCHMAKING_MAX_TIME, ChronoUnit.DAYS)),
        round: Int? = null,
        turn: Player? = null,
        winner: Player? = null,
    ) {
        if (round != null && round < 0)
            throw InvalidGameStateException("Round must be positive")

        if (turn != null && phase != IN_PROGRESS)
            throw InvalidGameStateException("Turn can only be set when the game is in progress")

        if (turn == null && phase == IN_PROGRESS)
            throw InvalidGameStateException("Turn must be set when the game is in progress")

        if (winner != null && phase != FINISHED)
            throw InvalidGameStateException("Winner can only be set when the game is finished")

        if (winner == null && phase == FINISHED)
            throw InvalidGameStateException("Winner must be set when the game is finished")

        this.phase = phase
        this.phaseExpirationTime = phaseExpirationTime
        this.round = round
        this.turn = turn
        this.winner = winner
    }

    /**
     * Checks if the game phase has expired.
     *
     * @return true if the game phase has expired, false otherwise
     */
    fun phaseExpired(): Boolean =
        phaseExpirationTime.time < System.currentTimeMillis() && phase != FINISHED

    /**
     * The game phases.
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
        private const val MATCHMAKING_MAX_TIME = 2L // days
    }
}
