package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.Player
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
 * @property name The game session name.
 * @property player1 The first player.
 * @property player2 The second player.
 * @property state The current game state.
 * @property config The game configuration.
 */
@Entity
@Table(name = "games")
class Game(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "name")
    val name: String,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player1", nullable = false)
    val player1: Player,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player2", nullable = true)
    val player2: Player? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "state", nullable = false)
    val state: GameState,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "config", nullable = false)
    val config: GameConfig
)
