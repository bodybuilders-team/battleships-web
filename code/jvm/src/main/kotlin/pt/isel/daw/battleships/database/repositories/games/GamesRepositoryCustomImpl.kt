package pt.isel.daw.battleships.database.repositories.games

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.game.GameConfig
import pt.isel.daw.battleships.database.model.game.GameState

/**
 * Implementation of the [GamesRepositoryCustom].
 *
 * @property gamesRepository the repository of the games
 */
@Component
class GamesRepositoryCustomImpl(@Lazy val gamesRepository: GamesRepository) : GamesRepositoryCustom {

    override fun findFirstAvailableGameWithConfig(config: GameConfig): Game? =
        gamesRepository
            .findAllByStatePhase(GameState.GamePhase.WAITING_FOR_PLAYERS)
            .find { it.config == config }
}
