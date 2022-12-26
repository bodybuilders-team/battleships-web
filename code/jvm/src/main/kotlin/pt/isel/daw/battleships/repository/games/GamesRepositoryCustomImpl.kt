package pt.isel.daw.battleships.repository.games

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameConfig
import pt.isel.daw.battleships.domain.games.game.GameState
import java.util.stream.Stream

/**
 * Implementation of the [GamesRepositoryCustom].
 *
 * @property gamesRepository the repository of the games
 */
@Component
class GamesRepositoryCustomImpl(@Lazy val gamesRepository: GamesRepository) : GamesRepositoryCustom {

    override fun findAllAvailableGamesWithConfig(config: GameConfig): Stream<Game> =
        gamesRepository
            .findAllByStatePhase(phase = GameState.GamePhase.WAITING_FOR_PLAYERS)
            .filter { game -> game.config == config }

    override fun findAllByPlayerAndStatePhase(
        player: String,
        phase: GameState.GamePhase,
        offset: Int,
        limit: Int
    ): Stream<Game> =
        gamesRepository
            .findAllByStatePhase(phase = phase)
            .filter { game -> game.players.any { it.user.username == player } }
            .skip(offset.toLong())
            .limit(limit.toLong())
}
