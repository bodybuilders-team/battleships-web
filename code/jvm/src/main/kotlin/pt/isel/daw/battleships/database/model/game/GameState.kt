package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.player.Player
import java.io.Serializable
import javax.persistence.*

/**
 * The GameState entity.
 *
 * @property game The game id.
 * @property round The current round.
 * @property turn The current player.
 * @property winner The winner player.
 */
@Embeddable
class GameState(
    @Column(name = "phase", nullable = false)
    @Enumerated(EnumType.STRING)
    var phase: GamePhase = GamePhase.WAITING_FOR_PLAYERS,

    @Column(name = "round", nullable = false)
    val round: Int? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "turn", nullable = true)
    val turn: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = true)
    val winner: Player? = null
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
}
