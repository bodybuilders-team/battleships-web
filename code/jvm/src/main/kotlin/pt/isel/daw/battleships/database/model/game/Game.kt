package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.player.Player
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
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
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "creator")
    val creator: User,

    @Column(name = "name")
    val name: String,

    @Embedded
    val state: GameState,

    @Embedded
    val config: GameConfig
) {
    init {
        config.shipTypes.forEach {
            it.shipTypeId.game = this
        }
    }

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "game_id")
    private val players: MutableList<Player> = mutableListOf()

    fun getPlayer(username: String): Player? {
        return players.find { it.playerPk.user.username == username }
    }

    fun getOpponent(username: String): Player? {
        return players.find { it.playerPk.user.username != username }
    }

    fun addPlayer(player: Player) {
        if (players.size >= 2) {
            throw IllegalStateException("Game already has two players")
        }

        players.add(player)
    }
}
