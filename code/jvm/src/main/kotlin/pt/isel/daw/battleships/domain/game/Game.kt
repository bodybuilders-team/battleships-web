package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.service.exceptions.NotFoundException
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
 * @property config the game configuration
 * @property state the current game state
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
    var id: Int? = null

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "game")
    val players: MutableList<Player> = mutableListOf()

    init {
        // This is a workaround for JPA instatiation of the entity with the no args constructor
        @Suppress("SAFE_CALL_WILL_CHANGE_NULLABILITY", "UNNECESSARY_SAFE_CALL")
        config?.shipTypes?.forEach { shipType ->
            shipType.game = this
        }
    }

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
     * Checks if the player is playing the game.
     *
     * @param username the username of the player
     * @return true if the player is playing the game, false otherwise
     */
    fun hasPlayer(username: String): Boolean = getPlayerOrNull(username) != null

    /**
     * Returns the player that is playing the game.
     * If the player is not playing the game, returns null.
     *
     * @param username the username of the player
     * @return the player that is playing the game or null
     */
    @Suppress("MemberVisibilityCanBePrivate")
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

    /**
     * Checks if the fleet of both players is ready.
     *
     * @return true if the fleet of both players is ready, false otherwise
     */
    fun areFleetsDeployed(): Boolean =
        players.all { it.deployedShips.isNotEmpty() }

    companion object {
        private const val MAX_GAME_PLAYERS = 2
    }
}
