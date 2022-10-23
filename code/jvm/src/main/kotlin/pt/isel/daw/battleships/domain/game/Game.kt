package pt.isel.daw.battleships.domain.game

import pt.isel.daw.battleships.domain.Player
import pt.isel.daw.battleships.domain.User
import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.WaitingForPlayersTimeExpiredException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import java.sql.Timestamp
import java.time.Instant
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
class Game {
    @Column(name = "name")
    val name: String

    @ManyToOne
    @JoinColumn(name = "creator")
    val creator: User

    @Embedded
    val config: GameConfig

    @Embedded
    val state: GameState

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "game")
    val players: MutableList<Player> = mutableListOf()

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(name: String, creator: User, config: GameConfig, state: GameState) {
        config.shipTypes.forEach { shipType ->
            shipType.game = this
        }

        this.name = name
        this.creator = creator
        this.config = config
        this.state = state
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

    // TODO: Comment function
    fun updatePhase() {
        state.phase = if (state.phase == GameState.GamePhase.IN_PROGRESS) {
            state.phase
        } else state.phase.next()

        state.phaseExpirationTime = Timestamp.from(
            Instant.now().plusSeconds(
                when (state.phase) {
                    GameState.GamePhase.DEPLOYING_FLEETS -> config.maxTimeForLayoutPhase
                    GameState.GamePhase.IN_PROGRESS -> config.maxTimePerRound
                    else -> 0
                }.toLong()
            )
        )
    }

    fun abortGame() {
        state.phase = GameState.GamePhase.FINISHED
    }

    fun finishGame(winner: Player) {
        state.phase = GameState.GamePhase.FINISHED
        state.winner = winner

        players.forEach {
            it.user.points += it.points
        }
    }

    /**
     * Updates the game state if the current phase has expired.
     */
    fun updateIfPhaseExpired() {
        if (!state.phaseExpired()) {
            return
        }

        if (state.phase == GameState.GamePhase.IN_PROGRESS) {
            val currentPlayer =
                state.turn ?: throw IllegalStateException("Game is in progress but turn is null")
            val winner = getOpponent(currentPlayer.user.username)

            finishGame(winner)
        } else {
            abortGame()
        }

        when (state.phase) {
            GameState.GamePhase.WAITING_FOR_PLAYERS ->
                throw WaitingForPlayersTimeExpiredException("The waiting for players time has expired.")

            GameState.GamePhase.DEPLOYING_FLEETS ->
                throw FleetDeployTimeExpiredException("The fleet deploy time has expired.")

            GameState.GamePhase.IN_PROGRESS ->
                throw FiringShotsTimeExpiredException("The firing shots time has expired.")

            GameState.GamePhase.FINISHED ->
                throw IllegalStateException("Game is already finished")
        }
    }

    fun isFinished(): Boolean = state.phase == GameState.GamePhase.FINISHED

    companion object {
        private const val MAX_GAME_PLAYERS = 2
    }

    enum class FinishedState {
        NOT_FINISHED,
        FINISHED,
        ABORTED
    }
}
