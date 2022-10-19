package pt.isel.daw.battleships.database.repositories.games

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState.GamePhase

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
     * @return the games that are in the given phase
     */
    fun findAllByStatePhase(phase: GamePhase): List<Game>
}

/**
 * Custom methods for the [GamesRepository].
 */
interface GamesRepositoryCustom {

    /**
     * Finds the first available game with the given configuration and wich the given user is not in it.
     *
     * @param user the user that is looking for a game
     * @param config the configuration of the game
     *
     * @return the first available game with the given configuration or null if there is none
     */
    fun findFirstAvailableGameWithConfig(user: User, config: GameConfig): Game?
}
