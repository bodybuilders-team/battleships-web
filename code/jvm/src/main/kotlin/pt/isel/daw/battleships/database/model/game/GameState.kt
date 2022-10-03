package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.player.Player
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.OneToOne

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
    val phase: GamePhase = GamePhase.WAITING_FOR_PLAYERS,

    @Column(name = "round", nullable = false)
    val round: Int = 0,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumns(
        JoinColumn(name = "id", referencedColumnName = "game_id", insertable = false, updatable = false),
        JoinColumn(name = "turn", referencedColumnName = "username", insertable = false, updatable = false)
    )
    val turn: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumns(
        JoinColumn(name = "id", referencedColumnName = "game_id", insertable = false, updatable = false),
        JoinColumn(name = "winner", referencedColumnName = "username", insertable = false, updatable = false)
    )
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
