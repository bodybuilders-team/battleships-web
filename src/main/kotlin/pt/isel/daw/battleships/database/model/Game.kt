package pt.isel.daw.battleships.database.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * The GameResponse entity.
 *
 * @property id The game id.
 * @property sessionName The game session name.
 * @property player1 The first player.
 * @property player2 The second player.
 * @property phase The current game phase.
 * @property round The current round.
 * @property turn The current player.
 * @property winner The winner player.
 */
@Entity
@Table(name = "games")
class Game(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "session_name")
    val sessionName: String,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player1", nullable = true)
    val player1: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player2", nullable = true)
    val player2: Player? = null,

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
