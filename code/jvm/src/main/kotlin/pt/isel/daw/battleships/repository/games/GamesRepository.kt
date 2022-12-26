package pt.isel.daw.battleships.repository.games

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.domain.games.game.GameState.GamePhase
import java.util.stream.Stream

/**
 * Repository for the [Game] entity.
 */
interface GamesRepository : JpaRepository<Game, String>, GamesRepositoryCustom {

    /**
     * Finds a game by its ID.
     *
     * @param id the ID of the game
     * @return the game with the given ID or null if it doesn't exist
     */
    fun findById(id: Int): Game?

    /**
     * Finds all games that are in the given phase.
     *
     * @param phase the phase of the games
     * @return stream with all the games that are in the given phase
     */
    fun findAllByStatePhase(phase: GamePhase): Stream<Game>
}

/**
 * Custom methods for the [GamesRepository].
 */
interface GamesRepositoryCustom {

    /**
     * Finds all available games with the given configuration.
     *
     * @param config the configuration of the game
     *
     * @return stream with all the available games with the given configuration
     */
    fun findAllAvailableGamesWithConfig(config: GameConfig): Stream<Game>

    /**
     * Finds the games that have the giver player, are in the given phase, with the giver offset and limit.
     *
     * @param player the username of the player
     * @param phase the phase of the games
     * @param offset the offset of the pagination
     * @param limit the limit of the pagination
     *
     * @return stream with the games that have the giver player, are in the given phase, with the giver offset and limit
     */
    fun findAllByPlayerAndStatePhase(player: String, phase: GamePhase, offset: Int, limit: Int): Stream<Game>
}
