package pt.isel.daw.battleships.database.repositories

import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState.GamePhase

/**
 * Repository for the [Game] entity.
 */
interface GamesRepository : CrudRepository<Game, String>, GamesRepositoryCustom {

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
     * @return the games that are in the given phase
     */
    fun findAllByStatePhase(phase: GamePhase): List<Game>
}

/**
 * Custom methods for the [GamesRepository].
 *
 */
interface GamesRepositoryCustom {

    /**
     * Finds the first available game with the given configuration.
     *
     * @param config the configuration of the game
     * @return the first available game with the given configuration or null if there is none
     */
    fun findFirstAvailableGameWithConfig(config: GameConfig): Game?
}

/**
 * Implementation of the [GamesRepositoryCustom].
 *
 * @property gamesRepository the repository of the games
 */
@Component
class GamesRepositoryCustomImpl(@Lazy val gamesRepository: GamesRepository) : GamesRepositoryCustom {

    override fun findFirstAvailableGameWithConfig(config: GameConfig): Game? =
        gamesRepository
            .findAllByStatePhase(GamePhase.WAITING_FOR_PLAYERS)
            .find { it.config == config }
}
