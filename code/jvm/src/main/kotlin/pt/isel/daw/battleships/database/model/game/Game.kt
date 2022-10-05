package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.player.Player
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import javax.persistence.*

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
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    init {
        config.shipTypes.forEach {
            it.game = this
        }
    }

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "game")
    val players: MutableList<Player> = mutableListOf()

    fun getPlayer(username: String): Player {
        return getPlayerOrNull(username)
            ?: throw NotFoundException("Player with username $username is not part of the game")
    }

    fun getPlayerOrNull(username: String): Player? {
        return players.find { it.user.username == username }
    }

    fun getOpponent(username: String): Player {
        return players.find { it.user.username != username }
            ?: throw NotFoundException("There's no opponent yet")
    }

    fun addPlayer(player: Player) {
        if (players.size >= 2) {
            throw IllegalStateException("Game already has two players")
        }

        players.add(player)
    }
}
