package pt.isel.daw.battleships.database.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * The Game entity.
 *
 * @property id The game id.
 * @property player1 The first player.
 * @property player2 The second player.
 * @property currentPlayer The current player.
 * @property winner The winner player.
 * @property currentRound The current round.
 * @property state The game state.
 */
@Entity
@Table(name = "games")
class Game(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player1", nullable = false)
    val player1: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player2", nullable = false)
    val player2: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "current_player", nullable = false)
    val currentPlayer: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "winner", nullable = false)
    val winner: Player? = null,

    @Column(name = "current_round", nullable = false)
    val currentRound: Int = 0,

    @Column(name = "state", nullable = false)
    val state: Int = 0
)
