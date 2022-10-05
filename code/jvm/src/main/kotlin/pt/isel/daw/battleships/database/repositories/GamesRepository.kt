package pt.isel.daw.battleships.database.repositories

import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState.GamePhase

/**
 * Repository for the GameResponse entity.
 */
interface GamesRepository : CrudRepository<Game, String>, GamesRepositoryCustom {
    fun findById(id: Int): Game?

    fun findAllByStatePhase(phase: GamePhase): List<Game>
}

interface GamesRepositoryCustom {
    fun findFirstAvailableGameWithConfig(config: GameConfig): Game?
}

@Component
class GamesRepositoryCustomImpl(@Lazy val gamesRepository: GamesRepository) : GamesRepositoryCustom {
    override fun findFirstAvailableGameWithConfig(config: GameConfig): Game? =
        gamesRepository.findAllByStatePhase(GamePhase.WAITING_FOR_PLAYERS).find {
            it.config == config
        }
}
