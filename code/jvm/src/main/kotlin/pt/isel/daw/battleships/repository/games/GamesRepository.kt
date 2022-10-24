package pt.isel.daw.battleships.repository.games

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameConfig
import pt.isel.daw.battleships.domain.game.GameState.GamePhase
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
}
