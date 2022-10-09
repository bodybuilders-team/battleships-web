package pt.isel.daw.battleships.database.model.game

import pt.isel.daw.battleships.database.model.Player
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.services.exceptions.NotFoundException
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
 * @property id the game id
 * @property name the game session name
 * @property creator the user that created the game
 * @property state the current game state
 * @property config the game configuration
 * @property players the players that are playing the game
 */
@Entity
@Table(name = "games")
class Game(
    @Column(name = "name")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "creator")
    val creator: User,

    @Embedded
    val config: GameConfig,

    @Embedded
    val state: GameState
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    init {
        config.shipTypes.forEach { shipType ->
            shipType.game = this
        }
    }

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "game")
    val players: MutableList<Player> = mutableListOf()

    /**
     * Returns the player that is playing the game.
     *
     * @param username the username of the player
     *
     * @return the player that is playing the game
     * @throws NotFoundException if the player is not playing the game
     */
    fun getPlayer(username: String): Player =
        getPlayerOrNull(username)
            ?: throw NotFoundException("Player with username $username is not part of the game")

    /**
     * Returns the player that is playing the game.
     * If the player is not playing the game, returns null.
     *
     * @param username the username of the player
     * @return the player that is playing the game or null
     */
    fun getPlayerOrNull(username: String): Player? =
        players.find { it.user.username == username }

    /**
     * Returns the opponent of the player that is playing the game.
     *
     * @param username the username of the player
     *
     * @return the opponent of the player that is playing the game
     * @throws NotFoundException if there is no opponent yet
     */
    fun getOpponent(username: String): Player =
        players.find { it.user.username != username }
            ?: throw NotFoundException("There's no opponent yet")

    /**
     * Adds a player to the game.
     *
     * @param player the player to add
     * @throws IllegalStateException if the game is already full
     */
    fun addPlayer(player: Player) {
        if (players.size >= MAX_GAME_PLAYERS) {
            throw IllegalStateException("Game already has two players")
        }

        players.add(player)
    }

    companion object {
        private const val MAX_GAME_PLAYERS = 2
    }
}
